package com.syd.common.bean.tree;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 树节点父类
 *
 * @param <K> 节点id类型, 需要实现equals和hashcode方法
 * @param <V> 节点类型
 * @author songyide
 */
public interface ITreeNode<K, V extends ITreeNode<K, V>> {
    /**
     * 获取默认比较器，null值在前
     *
     * @return 默认比较器
     * @throws ClassCastException 如果id不可比较
     */
    static <K, V extends ITreeNode<K, V>> Comparator<V> getDefaultComparator() {
        return Comparator.nullsFirst((o1, o2) -> {
            // 出于性能考虑不使用类型检查，id不支持排序或有其他排序需求自行传入比较器
            @SuppressWarnings("unchecked")
            Comparable<? super K> k1 = (Comparable<? super K>)o1.getId();
            return k1.compareTo(o2.getId());
        });
    }

    /**
     * 根据所有树节点列表，生成含有所有树形结构的列表
     * <p>使用默认排序方法{@link #getDefaultComparator()}
     *
     * @param nodes 树形节点列表
     * @return 树形结构列表
     */
    static <K, V extends ITreeNode<K, V>> List<V> generateTree(@NonNull List<? extends V> nodes) {
        return generateTree(nodes, getDefaultComparator());
    }

    /**
     * 根据所有树节点列表，生成含有所有树形结构的列表
     *
     * @param nodes 树形节点列表
     * @param cmp   比较器，比较器为null时则保持原列表相对顺序
     * @return 树形结构列表
     */
    static <K, V extends ITreeNode<K, V>> List<V> generateTree(
            @NonNull List<? extends V> nodes,
            @Nullable Comparator<? super V> cmp) {
        Map<K, V> map = nodes.stream().collect(Collectors.toMap(ITreeNode::getId, v -> v));
        List<V> roots = new ArrayList<>();
        var stream = nodes.stream();
        if (cmp != null) {
            stream = stream.sorted(cmp);
        }
        stream.forEach(node -> {
            if (node.isRoot()) {
                roots.add(node);
                return;
            }
            V parent = map.get(node.getParentId());
            if (parent != null) {
                List<V> children = parent.getChildren();
                if (children == null) {
                    children = new ArrayList<>();
                    parent.setChildren(children);
                }
                children.add(node);
            }
        });
        return roots;
    }

    /**
     * 获取节点id
     *
     * @return 树节点id
     */
    K getId();

    /**
     * 获取该节点的父节点id
     *
     * @return 父节点id
     */
    K getParentId();

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
    List<V> getChildren();

    /**
     * 设置节点的子节点列表
     *
     * @param children 子节点
     */
    void setChildren(List<V> children);
}