package com.syd.canal.event;

import com.alibaba.otter.canal.protocol.CanalEntry;

/**
 * 以接口方式使用的canal事件监听器，用于非Spring环境使用
 *
 * @author jigua
 * @date 2018/3/19
 */
public interface CanalEventListener {

    /**
     * run when event was fired
     *
     * @param eventType eventType
     * @param rowData   rowData
     */
    void onEvent(CanalEntry.EventType eventType, CanalEntry.RowData rowData);

}

