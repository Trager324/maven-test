package com.syd.common.bean.bo.header;

import org.springframework.lang.NonNull;

import java.util.Collections;
import java.util.List;

/**
 * 表头构建器
 *
 * @implSpec 线程安全
 */
public abstract class AbstractHeaderCreator<V extends HeaderItemContext> {
    public final <T> TableHeaderData<T> create(T data, V context) {
        return TableHeaderData.of(TableHeader.of(getItems(context)), data);
    }

    public final List<List<String>> getLabelList(V context) {
        return getItems(context)
                .stream()
                .map(i -> Collections.singletonList(i.label()))
                .toList();
    }

    public final List<String> getFieldList(V context) {
        return getItems(context)
                .stream()
                .map(HeaderItem::key)
                .toList();
    }

    /**
     * 根据参数获取表头项，并设置在上下文中进行缓存
     *
     * @param context 可以存储上下文的查询对象
     * @return 表头
     */
    public final List<HeaderItem> getItems(V context) {
        List<HeaderItem> items = context.getItems();
        if (items == null) {
            items = doGetItems(context);
            context.setItems(items);
        }
        return items;
    }

    /**
     * 根据参数获取表头项
     *
     * @param context 上下文
     * @return 表头
     */
    @NonNull
    protected abstract List<HeaderItem> doGetItems(V context);
}
