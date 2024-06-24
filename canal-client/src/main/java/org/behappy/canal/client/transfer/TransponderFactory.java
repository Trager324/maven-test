package org.behappy.canal.client.transfer;

import com.alibaba.otter.canal.client.CanalConnector;
import org.behappy.canal.client.ListenerPoint;
import org.behappy.canal.config.CanalConfig;
import org.behappy.canal.event.CanalEventListener;

import java.util.List;
import java.util.Map;

/**
 * 转发器工厂
 *
 * @author jigua
 * @date 2018/3/23
 */
public interface TransponderFactory {

    /**
     * 获取新转发器
     *
     * @param connector     connector
     * @param config        config
     * @param listeners     listeners
     * @param annoListeners annoListeners
     * @return MessageTransponder
     */
    MessageTransponder newTransponder(CanalConnector connector,
                                      Map.Entry<String, CanalConfig.Instance> config,
                                      List<CanalEventListener> listeners,
                                      List<ListenerPoint> annoListeners);
}

