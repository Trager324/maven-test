package com.syd.canal.client.transfer;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.syd.canal.annotation.ListenPoint;
import com.syd.canal.client.ListenerPoint;
import com.syd.canal.config.CanalConfig;
import com.syd.canal.event.CanalEventListener;
import com.syd.common.util.ExtStrUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * @author jigua
 * @date 2018/3/23
 */
@Slf4j
public class DefaultMessageTransponder extends AbstractBasicMessageTransponder {

    public DefaultMessageTransponder(CanalConnector connector,
                                     Map.Entry<String, CanalConfig.Instance> config,
                                     List<CanalEventListener> listeners,
                                     List<ListenerPoint> annoListeners) {
        super(connector, config, listeners, annoListeners);
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
    @Override
    protected Predicate<Map.Entry<Method, ListenPoint>> getAnnotationFilter(String destination,
                                                                            String schemaName,
                                                                            String tableName,
                                                                            CanalEntry.EventType eventType) {
        // 根据canal实例名过滤
        Predicate<Map.Entry<Method, ListenPoint>> df = e -> ExtStrUtils.isEmpty(e.getValue().destination())
                || e.getValue().destination().equals(destination);
        // 根据数据库schema过滤
        Predicate<Map.Entry<Method, ListenPoint>> sf = e -> e.getValue().schema().length == 0
                || Arrays.asList(e.getValue().schema()).contains(schemaName);
        // 根据数据表名过滤
        Predicate<Map.Entry<Method, ListenPoint>> tf = e -> e.getValue().table().length == 0
                || Arrays.asList(e.getValue().table()).contains(tableName);
        // 根据消息类型过滤
        Predicate<Map.Entry<Method, ListenPoint>> ef = e -> e.getValue().eventType().length == 0
                || Arrays.stream(e.getValue().eventType()).anyMatch(ev -> ev == eventType);
        return df.and(sf).and(tf).and(ef);
    }

    @Override
    protected Object[] getInvokeArgs(Method method, CanalEntry.EventType eventType,
                                     CanalEntry.RowData rowData,
                                     CanalEntry.Entry entry) {
        // 注入消息类型和监听到的数据，用于反射时调用
        return Arrays.stream(method.getParameterTypes())
                .map(p -> p == CanalEntry.EventType.class ? eventType
                        : p == CanalEntry.RowData.class ? rowData
                        : p == CanalEntry.Entry.class ? entry : null).toArray();
    }

    @Override
    protected List<CanalEntry.EntryType> getIgnoreEntryTypes() {
        // 过滤部分类型消息
        return Arrays.asList(CanalEntry.EntryType.TRANSACTIONBEGIN, CanalEntry.EntryType.TRANSACTIONEND, CanalEntry.EntryType.HEARTBEAT);
    }
}

