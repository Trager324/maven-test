package com.syd.common.bean.bo.header;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.syd.common.util.ExtCollectionUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * @author songyide
 * @date 2022/12/8
 */
@ApiModel("列信息")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record HeaderItem(@ApiModelProperty("字典键") String key,
                         @ApiModelProperty("标签") String label,
                         @ApiModelProperty("子列") List<HeaderItem> children) {
    public static HeaderItem of(String key, String label) {
        return of(key, label, null);
    }

    public static HeaderItem of(String key, String label, List<HeaderItem> children) {
        return new HeaderItem(key, label, children);
    }

    /**
     * 表头树转为矩阵
     */
    public static List<List<HeaderItem>> treeToMatrix(List<HeaderItem> headers) {
        // EasyExcel导出时会补齐表头
        return treeToMatrix(headers, false);
    }

    /**
     * 表头树转为矩阵
     */
    public static List<List<HeaderItem>> treeToMatrix(List<HeaderItem> headers, boolean needFill) {
        List<List<HeaderItem>> mat = new ArrayList<>();
        int height = dfs(mat, headers, new ArrayDeque<>());
        if (needFill) {
            // 按照最大高度填充列
            for (List<HeaderItem> col : mat) {
                HeaderItem last = col.get(col.size() - 1);
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
    public static List<List<String>> getExcelHeader(List<List<HeaderItem>> matrix) {
        return matrix.stream()
                .map(col -> col.stream().map(HeaderItem::label).toList())
                .toList();
    }

    /**
     * 根据{@link #treeToMatrix(List)}获得的矩阵转为叶子节点流
     */
    public static Stream<HeaderItem> getLeavesStream(List<List<HeaderItem>> matrix) {
        return matrix.stream()
                .map(col -> col.get(col.size() - 1));
    }

    /**
     * dfs获取树的矩阵形式，遍历根到叶子节点路径即可
     *
     * @return 树的高度
     */
    static int dfs(List<List<HeaderItem>> res, List<HeaderItem> list, Deque<HeaderItem> path) {
        if (ExtCollectionUtils.isEmpty(list)) {
            // 遇到叶子节点时将当前路径计入矩阵
            if (!ExtCollectionUtils.isEmpty(path))
                res.add(new ArrayList<>(path));
            return 0;
        }
        int h = 0;
        for (HeaderItem item : list) {
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

    @Data
    @Accessors(fluent = true, chain = true)
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

        public HeaderItem build() {
            List<HeaderItem> list = null;
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
