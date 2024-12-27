package org.behappy.java;


import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.util.Map;

import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;


public class Main {


    static void f1(Map<String, ? extends Map<String, Integer>> map) {

    }

    public static boolean isUnsignedLongPowerOf2(long n) {
        return n != 0 && (n & (n - 1)) == 0;
    }

    public static void main(String[] args) throws IOException, InterruptedException {

//        RRateLimiter rrl = new RedissonRateLimiter(null, "");
//        RateLimiter rl;
//        WatchService ws;
//        FileSystemWatcher fsw = new FileSystemWatcher();
//        fsw.addListener(cfss -> {
//            System.out.println("File change");
//            for (var cfs : cfss) {
//                for (var cf : cfs.getFiles()) {
//                    System.out.println(cf.getFile());
//                }
//            }
//        });
//        fsw.addSourceDirectory(new File("."));
//        fsw.start();
//        System.in.read();
        var dir = Path.of("./1");
        try (var watcher = FileSystems.getDefault().newWatchService();) {
            dir.register(watcher, ENTRY_MODIFY);
            for (; ; ) {

                // wait for key to be signaled
                WatchKey key;
                try {
                    key = watcher.take();
                } catch (InterruptedException x) {
                    return;
                }

                for (var event : key.pollEvents()) {
                    var kind = event.kind();

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
                    var ev = (WatchEvent<Path>) event;
                    Path filename = ev.context();

                    // Verify that the new
                    //  file is a text file.
                    try {
                        // Resolve the filename against the directory.
                        // If the filename is "test" and the directory is "foo",
                        // the resolved name is "test/foo".
                        Path child = dir.resolve(filename);
                        if (!Files.probeContentType(child).equals("text/plain")) {
                            System.err.format("New file '%s'" +
                                              " is not a plain text file.%n", filename);
                            continue;
                        }
                    } catch (IOException x) {
                        System.err.println(x);
                        continue;
                    }

                    // Email the file to the
                    //  specified email alias.
                    System.out.format("Emailing file %s%n", filename);
                    //Details left to reader....
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
    }
}
