package org.behappy.common.bean.tree;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.behappy.common.util.ExtCollectionUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author songyide
 * @date 2022/12/8
 */
@ApiModel("树形筛选实体")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record TreeItem(@ApiModelProperty("结点键") @JSONField(ordinal = 1) String key,
                       @ApiModelProperty("结点标签") @JSONField(ordinal = 2) String label,
                       @ApiModelProperty("子结点") @JSONField(ordinal = 3) @JsonPropertyOrder() List<TreeItem> children) {
    public static TreeItem of(String key, String label) {
        return of(key, label, null);
    }

    public static TreeItem of(String key, String label, List<TreeItem> children) {
        return new TreeItem(key, label, children);
    }

    /**
     * treeNode关系列表转换生成
     * <p>调用此方法前无需也不应调用{@link ITreeNode#generateTree(List)}
     */
    @NonNull
    public static <V extends StringTreeNode<V>> List<TreeItem> fromTreeNodeList(@Nullable List<? extends V> list) {
        return fromTreeNodeList(list, ITreeNode.getDefaultComparator());
    }

    /**
     * treeNode关系列表转换生成
     * <p>调用此方法前无需也不应调用{@link ITreeNode#generateTree(List, Comparator)}
     *
     * @param list 列表
     * @param cmp  比较器，比较器为null时则保持原列表相对顺序
     */
    @NonNull
    public static <V extends StringTreeNode<V>> List<TreeItem> fromTreeNodeList(@Nullable List<? extends V> list,
                                                                                @Nullable Comparator<? super V> cmp) {
        if (ExtCollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        return Objects.requireNonNull(genFromTreeNodeList0(ITreeNode.generateTree(list, cmp)));
    }

    private static <T extends StringTreeNode<T>> List<TreeItem> genFromTreeNodeList0(List<T> list) {
        if (ExtCollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.stream()
                .map(t -> TreeItem.of(t.getId(), t.getName(), genFromTreeNodeList0(t.getChildren())))
                .collect(Collectors.toList());
    }

    /**
     * 表头树转为矩阵
     */
    public static List<List<TreeItem>> treeToMatrix(List<TreeItem> headers) {
        // EasyExcel导出时会补齐表头
        return treeToMatrix(headers, false);
    }

    /**
     * 表头树转为矩阵
     */
    public static List<List<TreeItem>> treeToMatrix(List<TreeItem> headers, boolean needFill) {
        List<List<TreeItem>> mat = new ArrayList<>();
        int height = dfs(mat, headers, new ArrayDeque<>());
        if (needFill) {
            // 按照最大高度填充列
            for (List<TreeItem> col : mat) {
                TreeItem last = col.get(col.size() - 1);
                while (col.size() < height) {
                    col.add(last);
                }
            }
        }
        return mat;
    }

    /**
     * 根据{@link #treeToMatrix(List)}获得的矩阵转为EasyExcel所需表头结构
     */
    public static List<List<String>> getExcelHeader(List<List<TreeItem>> matrix) {
        return matrix.stream()
                .map(col -> col.stream().map(TreeItem::label).collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    /**
     * 根据{@link #treeToMatrix(List)}获得的矩阵转为叶子节点流
     */
    public static Stream<TreeItem> getLeavesStream(List<List<TreeItem>> matrix) {
        return matrix.stream()
                .map(col -> col.get(col.size() - 1));
    }

    /**
     * dfs获取树的矩阵形式，遍历根到叶子节点路径即可
     *
     * @return 树的高度
     */
    static int dfs(List<List<TreeItem>> res, List<TreeItem> list, Deque<TreeItem> path) {
        if (ExtCollectionUtils.isEmpty(list)) {
            // 遇到叶子节点时将当前路径计入矩阵
            if (!ExtCollectionUtils.isEmpty(path))
                res.add(new ArrayList<>(path));
            return 0;
        }
        int h = 0;
        for (TreeItem item : list) {
            // 路径入栈
            path.offerLast(item);
            h = Math.max(h, dfs(res, item.children, path));
            // 路径出栈
            path.removeLast();
        }
        return h + 1;
    }

    public static void dfsFilter(Map<String, Builder> itemMap, Set<String> fieldSet) {
        if (ExtCollectionUtils.isEmpty(itemMap)) {
            return;
        }
        var it = itemMap.entrySet().iterator();
        while (it.hasNext()) {
            var next = it.next();
            if (fieldSet.contains(next.getKey())) {
                // 字段包含跳过即可
                continue;
            }
            var children = next.getValue().children();
            dfsFilter(children, fieldSet);
            if (ExtCollectionUtils.isEmpty(children) && !fieldSet.contains(next.getKey())) {
                // 叶子节点不在set中即可删除
                it.remove();
            }
        }
    }

    public static Builder builder() {
        return builder(null);
    }

    public static Builder builder(Comparator<String> cmp) {
        return new Builder().cmp(cmp);
    }

    @Accessors(fluent = true, chain = true)
    @Data
    public static class Builder {
        private Map<String, Builder> children = new TreeMap<>();
        private String originKey;
        private String key;
        private String label;
        private Comparator<String> cmp;

        public Builder computeIfAbsent(String key, Function<String, Builder> mappingFunction) {
            return this.children.computeIfAbsent(key, mappingFunction);
        }

        public Builder add(Builder builder) {
            children.put(builder.key, builder);
            return this;
        }

        public TreeItem build() {
            List<TreeItem> list = null;
            if (!ExtCollectionUtils.isEmpty(this.children)) {
                // 递归构建表头
                List<Builder> builderList = new ArrayList<>(this.children.values());
                if (cmp != null) {
                    builderList.sort((e1, e2) -> cmp.compare(e1.originKey, e2.originKey));
                }
                list = new ArrayList<>(builderList.size());
                for (Builder builder : builderList) {
                    list.add(builder.build());
                }
            }
            return of(key, label, list);
        }
    }
}
