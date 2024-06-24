package org.behappy.common.bean.tree;


import org.behappy.common.constant.ResponseCode;
import org.behappy.common.exception.BaseException;
import org.behappy.common.util.ExtCollectionUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author songyide
 * @date 2022/12/13
 */
public interface HeaderItemContext {
    /**
     * 获取表头项数组
     *
     * @param itemPairs 表头项键值列表
     * @return 表头项数组
     */
    static TreeItem[] getItemArrayFromItemPairs(String[][] itemPairs) {
        return Arrays.stream(itemPairs)
                .map(s -> TreeItem.of(s[0], s[1]))
                .toArray(TreeItem[]::new);
    }

    /**
     * 获取表头项列表{@link AbstractHeaderCreator#doGetItems}的协助方法
     *
     * @param ctx   表头列表上下文
     * @param items 表头项数组
     * @return 表头项列表
     */
    static List<TreeItem> getItemsFromItemArray(HeaderItemContext ctx, TreeItem[] items) {
        var categoryLabelList = ctx.getCategoryLabelList();
        if (categoryLabelList == null) {
            throw BaseException.of(ResponseCode.B0001).appendDebugInfo("维度表头未初始化");
        }
        // 维度项分隔索引
        var categoryIdx = categoryLabelList.size();
        // 维度项
        var categories = IntStream.range(0, categoryIdx)
                .mapToObj(i -> TreeItem.of(items[i].key(), categoryLabelList.get(i)));
        // 指标项
        var columns = Stream.of(items).skip(categoryIdx);
        var fieldList = ctx.getFieldList();
        // fieldList不为空表示需要筛选字段
        if (!ExtCollectionUtils.isEmpty(fieldList)) {
            // 表头项过多使用HashSet优化
            Collection<String> fieldSet = fieldList.size() * (items.length - categoryIdx) <= 1 << 8
                    ? fieldList : new HashSet<>(fieldList);
            columns = columns.filter(i -> fieldSet.contains(i.key()));
        }
        return Stream.concat(categories, columns)
                .collect(Collectors.toList());
    }

    /**
     * 获取表头项
     *
     * @return 表头项
     */
    List<TreeItem> getItems();

    /**
     * 设置表头项
     *
     * @param items 表头项
     */
    void setItems(List<TreeItem> items);

    /**
     * 获取筛选字段列表
     *
     * @return 筛选字段列表
     */
    default List<String> getFieldList() {
        return Collections.emptyList();
    }

    /**
     * 获取维度标签列表
     *
     * @return 维度标签列表
     */
    default List<String> getCategoryLabelList() {
        throw BaseException.of(ResponseCode.B0001, "调用此方法相关方法时请先重写此方法");
    }
}
