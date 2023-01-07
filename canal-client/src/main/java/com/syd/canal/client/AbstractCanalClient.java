package com.syd.canal.client;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.exception.CanalClientException;
import com.syd.canal.client.transfer.TransponderFactory;
import com.syd.canal.config.CanalConfig;
import com.syd.common.util.ExtStrUtils;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.Objects;

/**
 * Abstract implements of the CanalClient interface It help to initialize the canal connector and so on.
 *
 * @author jigua
 */
public abstract class AbstractCanalClient implements CanalClient {

    /**
     * TransponderFactory
     */
    protected final TransponderFactory factory;
    /**
     * customer config
     */
    private final CanalConfig canalConfig;
    /**
     * running flag
     */
    private volatile boolean running;

    AbstractCanalClient(CanalConfig canalConfig, TransponderFactory factory) {
        Objects.requireNonNull(canalConfig, "canalConfig can not be null!");
        Objects.requireNonNull(canalConfig, "transponderFactory can not be null!");
        this.canalConfig = canalConfig;
        this.factory = factory;
        this.running = true;
    }

    /**
     * {@inheritDoc}
     * <p>开启canal客户端，开启canal连接，并为每个实例开启一条转发器线程，由转发器轮询canal服务端消息
     */
    @Override
    public void start() {
        Map<String, CanalConfig.Instance> instanceMap = getConfig();
        for (Map.Entry<String, CanalConfig.Instance> instanceEntry : instanceMap.entrySet()) {
            process(processInstanceEntry(instanceEntry), instanceEntry);
        }
    }

    /**
     * To initialize the canal connector
     *
     * @param connector CanalConnector
     * @param config    config
     */
    protected abstract void process(CanalConnector connector, Map.Entry<String, CanalConfig.Instance> config);

    /**
     * 根据配置获取并初始化canal连接
     */
    private CanalConnector processInstanceEntry(Map.Entry<String, CanalConfig.Instance> instanceEntry) {
        CanalConfig.Instance instance = instanceEntry.getValue();
        CanalConnector connector;
        //        if (instance.isClusterEnabled()) {
//            List<SocketAddress> addresses = new ArrayList<>();
//            for (String s : instance.getZookeeperAddress()) {
//                String[] entry = s.split(":");
//                if (entry.length != 2){
//                    throw new CanalClientException("error parsing zookeeper address:" + s);
//                }
//                addresses.add(new InetSocketAddress(entry[0], Integer.parseInt(entry[1])));
//            }
//            connector = CanalConnectors.newClusterConnector(addresses, instanceEntry.getKey(),
//                    instance.getUserName(),
//                    instance.getPassword());
//        } else {
//
//        }
        connector = CanalConnectors.newSingleConnector(
                new InetSocketAddress(instance.getHost(), instance.getPort()),
                instanceEntry.getKey(),
                instance.getUsername(),
                instance.getPassword()
        );
        connector.connect();
        if (!ExtStrUtils.isEmpty(instance.getFilter())) {
            connector.subscribe(instance.getFilter());
        } else {
            connector.subscribe();
        }
        connector.rollback();
        return connector;
    }

    /**
     * get the config
     *
     * @return config
     */
    protected Map<String, CanalConfig.Instance> getConfig() {
        CanalConfig config = canalConfig;
        Map<String, CanalConfig.Instance> instanceMap;
        if ((instanceMap = config.getInstances()) != null && !instanceMap.isEmpty()) {
            return config.getInstances();
        } else {
            throw new CanalClientException("can not get the configuration of canal client!");
        }
    }

    @Override
    public void stop() {
        this.running = false;
    }

    @Override
    public boolean isRunning() {
        return running;
    }
}

