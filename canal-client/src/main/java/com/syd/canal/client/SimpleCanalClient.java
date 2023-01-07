package com.syd.canal.client;

import com.alibaba.otter.canal.client.CanalConnector;
import com.syd.canal.annotation.CanalListenPoint;
import com.syd.canal.annotation.ListenPoint;
import com.syd.canal.client.transfer.TransponderFactory;
import com.syd.canal.config.CanalConfig;
import com.syd.canal.event.CanalEventListener;
import com.syd.common.util.ExecutorUtils;
import com.syd.common.util.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 依赖于Spring的canal客户端
 *
 * @author jigua
 * @date 2018/3/16
 */
@Slf4j
public class SimpleCanalClient extends AbstractCanalClient {
    /**
     * listeners which are used by implementing the Interface
     */
    private final List<CanalEventListener> listeners = new ArrayList<>();

    /**
     * listeners which are used by annotation
     */
    private final List<ListenerPoint> annoListeners = new ArrayList<>();
    /**
     * executor
     */
    private final ThreadPoolExecutor executor;

    public SimpleCanalClient(CanalConfig canalConfig, TransponderFactory factory) {
        super(canalConfig, factory);
        // canal客户端实例监听线程池，启动后大小基本固定，目前没有动态调整canal实例需求
        executor = new ThreadPoolExecutor(canalConfig.getInstances().size(), Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<>(),
                ExecutorUtils.newThreadFactory("SimpleCanalClient"),
                ExecutorUtils.DEFAULT_HANDLER);
        initListeners();
    }

    @Override
    protected void process(CanalConnector connector, Map.Entry<String, CanalConfig.Instance> config) {
        // 为所有连接封装一个转发器
        executor.submit(factory.newTransponder(connector, config, listeners, annoListeners));
    }

    @Override
    public void stop() {
        super.stop();
        executor.shutdown();
    }

    /**
     * init listeners
     */
    private void initListeners() {
        log.info("{}: initializing the listeners....", Thread.currentThread().getName());
        Collection<CanalEventListener> list = SpringUtils.getBeansOfType(CanalEventListener.class).values();
        listeners.addAll(list);
        // 获取所有监听点
        Map<String, Object> listenerMap = SpringUtils.getBeansWithAnnotation(CanalListenPoint.class);
        for (Object target : listenerMap.values()) {
            Method[] methods = target.getClass().getDeclaredMethods();
            for (Method method : methods) {
                // 只能解析到ListenPoint
                // ListenPoint l = AnnotationUtils.findAnnotation(method, ListenPoint.class);
                // 子注解属性传递给父注解
                ListenPoint l = AnnotatedElementUtils.getMergedAnnotation(method, ListenPoint.class);
                if (l != null) {
                    // 获取所有需要监听的方法，新建调用器
                    annoListeners.add(new ListenerPoint(target, method, l));
                }
            }
        }
        log.info("{}: initializing the listeners end.", Thread.currentThread().getName());
        if (log.isWarnEnabled() && listeners.isEmpty() && annoListeners.isEmpty()) {
            log.warn("{}: No listener found in context! ", Thread.currentThread().getName());
        }
    }
}

