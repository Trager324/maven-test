package com.syd.canal.client.transfer;

import com.alibaba.otter.canal.client.CanalConnector;
import com.syd.canal.client.ListenerPoint;
import com.syd.canal.config.CanalConfig;
import com.syd.canal.event.CanalEventListener;

import java.util.List;
import java.util.Map;

/**
 * @author jigua
 * @date 2018/3/23
 */
public class DefaultTransponderFactory implements TransponderFactory {
    @Override
    public MessageTransponder newTransponder(CanalConnector connector,
                                             Map.Entry<String, CanalConfig.Instance> config,
                                             List<CanalEventListener> listeners,
                                             List<ListenerPoint> annoListeners) {
        return new DefaultMessageTransponder(connector, config, listeners, annoListeners);
    }
}

