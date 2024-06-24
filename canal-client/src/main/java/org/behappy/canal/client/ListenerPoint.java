package org.behappy.canal.client;


import org.behappy.canal.annotation.ListenPoint;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * ListenerPoint save the information of listener's method-info
 *
 * @author jigua
 * @date 2018/3/23
 */
public class ListenerPoint {
    private final Object target;
    private final Map<Method, ListenPoint> invokeMap = new HashMap<>();

    ListenerPoint(Object target, Method method, ListenPoint anno) {
        this.target = target;
        this.invokeMap.put(method, anno);
    }

    public Object getTarget() {
        return target;
    }

    public Map<Method, ListenPoint> getInvokeMap() {
        return invokeMap;
    }
}

