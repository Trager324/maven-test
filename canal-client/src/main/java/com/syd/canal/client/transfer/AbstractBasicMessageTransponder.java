package com.syd.canal.client.transfer;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.alibaba.otter.canal.protocol.exception.CanalClientException;
import com.syd.canal.annotation.ListenPoint;
import com.syd.canal.client.ListenerPoint;
import com.syd.canal.config.CanalConfig;
import com.syd.canal.event.CanalEventListener;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * @author jigua
 * @date 2018/4/2
 */
@Slf4j
public abstract class AbstractBasicMessageTransponder extends AbstractMessageTransponder {

    public AbstractBasicMessageTransponder(CanalConnector connector, Map.Entry<String, CanalConfig.Instance> config,
                                           List<CanalEventListener> listeners, List<ListenerPoint> annoListeners) {
        super(connector, config, listeners, annoListeners);
    }

    @Override
    protected void distributeEvent(Message message) {
        List<CanalEntry.Entry> entries = message.getEntries();
        for (CanalEntry.Entry entry : entries) {
            // ignore the transaction operations
            List<CanalEntry.EntryType> ignoreEntryTypes = getIgnoreEntryTypes();
            if (ignoreEntryTypes != null
                    && ignoreEntryTypes.stream().anyMatch(t -> entry.getEntryType() == t)) {
                continue;
            }
            CanalEntry.RowChange rowChange;
            try {
                rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                throw new CanalClientException("ERROR ## parser of event has an error , data:" + entry.toString(), e);
            }
            // ignore the ddl operation
            if (rowChange.hasIsDdl() && rowChange.getIsDdl()) {
                processDdl(rowChange);
                continue;
            }
            // 一个message可能包含多个entry，每个entry的事件类型相同，一个entry可能包含多个rowData数据变更，处理entry中的每个rowData
            for (CanalEntry.RowData rowData : rowChange.getRowDatasList()) {
                // 调用所有接口监听回调
                distributeByImpl(rowChange.getEventType(), rowData);
                // 调用所有注解监听点
                distributeByAnnotation(destination, entry, rowChange.getEventType(), rowData);
            }
        }
    }

    /**
     * process the ddl event
     *
     * @param rowChange rowChange
     */
    protected void processDdl(CanalEntry.RowChange rowChange) {
    }

    /**
     * distribute to listener interfaces
     *
     * @param eventType eventType
     * @param rowData   rowData
     */
    protected void distributeByImpl(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
        if (listeners != null) {
            for (CanalEventListener listener : listeners) {
                listener.onEvent(eventType, rowData);
            }
        }
    }

    /**
     * distribute to annotation listener interfaces
     *
     * @param destination destination
     * @param entry       entry
     * @param eventType   event type
     * @param rowData     row data
     */
    protected void distributeByAnnotation(String destination,
                                          CanalEntry.Entry entry,
                                          CanalEntry.EventType eventType,
                                          CanalEntry.RowData rowData) {
        //invoke the listeners
        annoListeners.forEach(point -> point
                .getInvokeMap()
                .entrySet()
                .stream()
                .filter(getAnnotationFilter(destination, entry.getHeader().getSchemaName(),
                        entry.getHeader().getTableName(), eventType))
                .forEach(e -> {
                    Method method = e.getKey();
                    method.setAccessible(true);
                    try {
                        // 反射调用监听点方法
                        Object[] args = getInvokeArgs(method, eventType, rowData, entry);
                        method.invoke(point.getTarget(), args);
                    } catch (Exception ex) {
                        log.error("{}: Error occurred when invoke the listener's interface! class:{}, method:{}",
                                Thread.currentThread().getName(),
                                point.getTarget().getClass().getName(), method.getName());
                        log.error(ex.getMessage(), ex);
                    }
                }));
    }

    /**
     * get the filters predicate
     *
     * @param destination destination
     * @param schemaName  schema
     * @param tableName   table name
     * @param eventType   event type
     * @return predicate
     */
    protected abstract Predicate<Map.Entry<Method, ListenPoint>> getAnnotationFilter(String destination,
                                                                                     String schemaName,
                                                                                     String tableName,
                                                                                     CanalEntry.EventType eventType);

    /**
     * get the args
     *
     * @param method    method
     * @param eventType event type
     * @param rowData   row data
     * @param entry     entry
     * @return args which will be used by invoking the annotation methods
     */
    protected abstract Object[] getInvokeArgs(Method method, CanalEntry.EventType eventType,
                                              CanalEntry.RowData rowData,
                                              CanalEntry.Entry entry);

    /**
     * get ignored eventType list
     *
     * @return eventType list
     */
    protected List<CanalEntry.EntryType> getIgnoreEntryTypes() {
        return Collections.emptyList();
    }

}

