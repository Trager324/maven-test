package com.syd.common.bean.tree;

import java.util.List;

/**
 * 字符串树形组件基类
 *
 * @author songyide
 * @date 2022/12/20
 */
public interface StringTreeNode<V extends StringTreeNode<V>> extends ITreeNode<String, V> {
    String getName();

    @Override
    default String getParentId() {
        return null;
    }

    @Override
    default boolean isRoot() {
        // 默认所有节点都是根节点
        return true;
    }

    @Override
    default List<V> getChildren() {
        return null;
    }

    @Override
    default void setChildren(List<V> children) {
    }
}
