package org.behappy.java.config.watcher;

import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;


public interface StandardWatchEventListener<T> {
    default ControlType accept(WatchEvent<T> event) throws Exception {
        WatchEvent.Kind<T> kind = event.kind();
        ControlType controlType = ControlType.CONTINUE;
        if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
            controlType = onCreate(event.context());
        } else if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
            controlType = onModify(event.context());
        } else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
            controlType = onDelete(event.context());
        } else if (kind == StandardWatchEventKinds.OVERFLOW) {
            controlType = onOverflow(event.context());
        }
        return controlType;
    }

    default ControlType onCreate(T context) throws Exception {
        return ControlType.CONTINUE;
    }

    default ControlType onModify(T context) throws Exception {
        return ControlType.CONTINUE;
    }

    default ControlType onDelete(T context) throws Exception {
        return ControlType.CONTINUE;
    }

    default ControlType onOverflow(T context) throws Exception {
        return ControlType.CONTINUE;
    }
}
