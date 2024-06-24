package org.behappy.canal.event;

import com.alibaba.otter.canal.protocol.CanalEntry;

import java.util.Objects;

/**
 * DmlCanalEventListener
 *
 * @author jigua
 * @date 2018/3/19
 */
public interface DmlCanalEventListener extends CanalEventListener {

    /**
     * {@inheritDoc}
     *
     * @param eventType eventType
     * @param rowData   rowData
     */
    @Override
    default void onEvent(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
        Objects.requireNonNull(eventType);
        switch (eventType) {
            case INSERT -> onInsert(rowData);
            case UPDATE -> onUpdate(rowData);
            case DELETE -> onDelete(rowData);
            default -> {
            }
        }
    }

    /**
     * fired on insert event
     *
     * @param rowData rowData
     */
    void onInsert(CanalEntry.RowData rowData);

    /**
     * fired on update event
     *
     * @param rowData rowData
     */
    void onUpdate(CanalEntry.RowData rowData);

    /**
     * fired on delete event
     *
     * @param rowData rowData
     */
    void onDelete(CanalEntry.RowData rowData);

}

