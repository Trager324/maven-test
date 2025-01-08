package org.behappy.java.config.watcher;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;
public class SimpleFileWatcher implements Runnable {
    private static final Log log = LogFactory.getLog(SimpleFileWatcher.class);
    final Path path;
    final List<? extends StandardWatchEventListener<Path>> listeners;

    public SimpleFileWatcher(Path path, List<? extends StandardWatchEventListener<Path>> listeners) {
        this.path = path;
        this.listeners = listeners;
    }

    @Override
    public void run() {
        log.info("SimpleFileWatcher start watching: " + path);
        while (!Thread.currentThread().isInterrupted()) {
            // wait for key to be signaled
            try (WatchService watcher = FileSystems.getDefault().newWatchService()) {
                watch(watcher);
            } catch (IOException e) {
                log.error("Creating WatchService error.", e);
            } catch (Exception e) {
                log.error("Unknown exception while watching.", e);
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        log.info("SimpleFileWatcher finish watching: " + path);
    }

    private void tryCreateWatchingDirectory() {
        try {
            log.info("Try create watching directory: " + path);
            Files.createDirectories(path);
        } catch (IOException e) {
            log.error("Could not create watching directory: " + path, e);
        }
    }

    private void watch(WatchService watcher) {
        try {
            path.register(watcher, ENTRY_MODIFY, ENTRY_DELETE);
        } catch (IOException e) {
            tryCreateWatchingDirectory();
            return;
        }
        for (; ; ) {
            // wait for key to be signaled
            WatchKey key;
            try {
                key = watcher.take();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.info("FileWatcher been interrupted", e);
                return;
            }

            ControlType controlType = ControlType.CONTINUE;
            for (WatchEvent<?> event : key.pollEvents()) {
                WatchEvent.Kind<?> kind = event.kind();

                // This key is registered only
                // for ENTRY_CREATE events,
                // but an OVERFLOW event can
                // occur regardless if events
                // are lost or discarded.
                if (kind == OVERFLOW) {
                    continue;
                }

                // The filename is the
                // context of the event.
                @SuppressWarnings("unchecked")
                WatchEvent<Path> ev = (WatchEvent<Path>) event;
                for (StandardWatchEventListener<Path> listener : listeners) {
                    try {
                        ControlType nextControlType = listener.accept(ev);
                        controlType = aggregateControlType(controlType, nextControlType);
                    } catch (Exception e) {
                        log.warn("FileWatcher listener error", e);
                    }
                }
            }
            if (controlType == ControlType.BREAK) {
                break;
            }

            // Reset the key -- this step is critical if you want to
            // receive further watch events.  If the key is no longer valid,
            // the directory is inaccessible so exit the loop.
            boolean valid = key.reset();
            if (!valid) {
                break;
            }
        }
    }

    protected ControlType aggregateControlType(ControlType prev, ControlType next) {
        return prev == ControlType.BREAK || next == ControlType.BREAK
                ? ControlType.BREAK : ControlType.CONTINUE;
    }

    public void start() {
        start("FileWatcher for " + path);
    }

    public void start(String name) {
        Thread t = new Thread(this);
        t.setDaemon(true);
        t.setName(name);
        t.start();
    }
}
