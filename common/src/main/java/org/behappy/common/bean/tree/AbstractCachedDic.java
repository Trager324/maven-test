package org.behappy.common.bean.tree;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public abstract class AbstractCachedDic<T extends StringTreeNode<T>> {
    private List<TreeItem> list;
    private Comparator<T> comparator = ITreeNode.getDefaultComparator();
    private volatile Map<String, T> map;

    @NonNull
    protected abstract Map<String, T> fetchMap();

    protected boolean needRefresh(Map<String, T> oldMap, Map<String, T> newMap) {
        return oldMap != newMap;
    }

    protected void beforeRefresh(Map<String, T> oldMap, Map<String, T> newMap) {
    }

    public final void refresh() {
        var newMap = fetchMap();
        if (needRefresh(map, newMap)) {
            synchronized (this) {
                beforeRefresh(map, newMap);
                list = TreeItem.fromTreeNodeList(new ArrayList<>(newMap.values()), comparator);
                map = newMap;
            }
        }
    }

    public final List<TreeItem> getList() {
        if (list == null) {
            synchronized (this) {
                refresh();
            }
        }
        return list;
    }

    public final synchronized Map<String, T> getMap() {
        if (map == null) {
            synchronized (this) {
                refresh();
            }
        }
        return map;
    }

    /**
     * {@link TreeItem#fromTreeNodeList(List, Comparator)}
     *
     * @param comparator 比较器，比较器为null时则保持原列表相对顺序
     * @return this
     */
    public final AbstractCachedDic<T> setComparator(@Nullable Comparator<T> comparator) {
        this.comparator = comparator;
        return this;
    }

    /**
     * 清空缓存，可通过懒汉式加载重新刷新缓存
     */
    public synchronized void clear() {
        this.map = null;
        this.list = null;
    }
}