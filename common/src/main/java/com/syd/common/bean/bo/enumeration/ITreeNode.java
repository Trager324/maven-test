package com.syd.common.bean.bo.enumeration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 树节点父类
 *
 * @param <T> 节点id类型
 * @author songyide
 */
public interface ITreeNode<T, R extends ITreeNode<T, R>> {
    /**
     * 根据所有树节点列表，生成含有所有树形结构的列表
     *
     * @param nodes 树形节点列表
     * @param <T>   id类型
     * @param <R>   节点类型
     * @return 树形结构列表
     */
    static <T, R extends ITreeNode<T, R>> List<R> generateTree(List<R> nodes) {
        Map<T, R> treeMap = new HashMap<>(nodes.size());
        nodes.forEach(e -> treeMap.put(e.id(), e));
        List<R> roots = new ArrayList<>();
        for (R node : nodes) {
            if (!node.isRoot()) {
                R parent = treeMap.get(node.parentId());
                if (parent != null) {
                    List<R> children = parent.getChildren();
                    if (children == null) {
                        children = new ArrayList<>();
                        parent.setChildren(children);
                    }
                    children.add(node);
                }
            } else {
                roots.add(node);
            }
        }
        return roots;
    }

    /**
     * 获取节点id
     *
     * @return 树节点id
     */
    T id();

    /**
     * 获取该节点的父节点id
     *
     * @return 父节点id
     */
    T parentId();

    /**
     * 是否是根节点
     *
     * @return true: 根节点
     */
    boolean isRoot();

    /**
     * 获取所有子节点
     *
     * @return 子节点列表
     */
    List<R> getChildren();

    /**
     * 设置节点的子节点列表
     *
     * @param children 子节点
     */
    void setChildren(List<R> children);

    /**
     * 是否叶子节点
     *
     * @return 是否叶子节点
     */
    default boolean isLeaf() {
        return getChildren().size() == 0;
    }
}