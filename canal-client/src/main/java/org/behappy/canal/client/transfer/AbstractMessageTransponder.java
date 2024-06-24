package org.behappy.canal.client.transfer;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.protocol.Message;
import com.alibaba.otter.canal.protocol.exception.CanalClientException;
import org.behappy.canal.client.ListenerPoint;
import org.behappy.canal.config.CanalConfig;
import org.behappy.canal.event.CanalEventListener;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Abstract implements of the MessageTransponder interface.
 *
 * @author jigua
 * @date 2018/3/19
 */
@Slf4j
public abstract class AbstractMessageTransponder implements MessageTransponder {
    /**
     * custom config
     */
    protected final CanalConfig.Instance config;

    /**
     * destination of canal server
     */
    protected final String destination;

    /**
     * 接口监听器
     */
    protected final List<CanalEventListener> listeners = new ArrayList<>();

    /**
     * 注解监听点
     */
    protected final List<ListenerPoint> annoListeners = new ArrayList<>();
    /**
     * canal connector
     */
    private final CanalConnector connector;
    /**
     * running flag
     */
    private volatile boolean running = true;

    public AbstractMessageTransponder(CanalConnector connector,
                                      Map.Entry<String, CanalConfig.Instance> config,
                                      List<CanalEventListener> listeners,
                                      List<ListenerPoint> annoListeners) {
        Objects.requireNonNull(connector, "connector can not be null!");
        Objects.requireNonNull(config, "config can not be null!");
        this.connector = connector;
        this.destination = config.getKey();
        this.config = config.getValue();
        if (listeners != null)
            this.listeners.addAll(listeners);
        if (annoListeners != null)
            this.annoListeners.addAll(annoListeners);
    }

    /**
     * 消息转发器线程方法，轮询消息分发给所有监听器
     */
    @Override
    @SuppressWarnings("all")
    public void run() {
        int errorCount = config.getRetryCount();
        final long interval = config.getAcquireInterval();
        final String threadName = Thread.currentThread().getName();
        while (running && !Thread.currentThread().isInterrupted()) {
            try {
                Message message = connector.getWithoutAck(config.getBatchSize());
                long batchId = message.getId();
                int size = message.getEntries().size();
                // empty message
                if (batchId == -1 || size == 0) {
                    Thread.sleep(interval);
                } else {
                    // 关键方法，调用所有监听回调
                    distributeEvent(message);
                }
                // 小于等于batchId的消息都会标记为已消费
                // TODO: 2022/10/18 batchId生成规则有待研究
                connector.ack(batchId);
            } catch (CanalClientException e) {
                errorCount--;
                log.error(threadName + ": Error occurred!! ", e);
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e1) {
                    errorCount = 0;
                }
            } catch (InterruptedException e) {
                errorCount = 0;
                connector.rollback();
            } finally {
                if (errorCount <= 0) {
                    stop();
                    log.info("{}: Topping the client.. ", Thread.currentThread().getName());
                }
            }
        }
        stop();
        log.info("{}: client stopped. ", Thread.currentThread().getName());
    }

    /**
     * to distribute the message to special event and let the event listeners to handle it
     *
     * @param message canal message
     */
    protected abstract void distributeEvent(Message message);

    /**
     * stop running
     */
    void stop() {
        running = false;
    }

}

