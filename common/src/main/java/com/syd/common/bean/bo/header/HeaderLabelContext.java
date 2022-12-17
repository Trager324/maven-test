package com.syd.common.bean.bo.header;

import com.syd.common.constant.ResponseCode;
import com.syd.common.exception.BaseException;
import com.syd.common.util.ExtCollectionUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 表头列表上下文
 *
 * @author songyide
 * @date 2022/12/14
 */
public interface HeaderLabelContext {
    /**
     * 获取表头项数组
     *
     * @param itemPairs 表头项键值列表
     * @return 表头项数组
     */
    static HeaderItem[] getItemArrayFromItemPairs(String[][] itemPairs) {
        return Arrays.stream(itemPairs)
                .map(s -> HeaderItem.of(s[0], s[1]))
                .toArray(HeaderItem[]::new);
    }

    /**
     * 获取表头项列表{@link AbstractHeaderCreator#doGetItems}的协助方法
     *
     * @param ctx   表头列表上下文
     * @param items 表头项数组
     * @return 表头项列表
     */
    static List<HeaderItem> getItemsFromItemArray(HeaderLabelContext ctx, HeaderItem[] items) {
        var categoryLabelList = ctx.getCategoryLabelList();
        if (categoryLabelList == null) {
            throw BaseException.of(ResponseCode.B0001).setDebugInfo("维度表头未初始化");
        }
        // 维度项分隔索引
        var categoryIdx = categoryLabelList.size();
        // 维度项
        var categories = IntStream.range(0, categoryIdx)
                .mapToObj(i -> HeaderItem.of(items[i].key(), categoryLabelList.get(i)));
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
        return Stream.concat(categories, columns).toList();
    }

    /**
     * 获取筛选字段列表
     *
     * @return 筛选字段列表
     */
    List<String> getFieldList();

    /**
     * 获取维度标签列表
     *
     * @return 维度标签列表
     */
    List<String> getCategoryLabelList();

    /**
     * 设置维度标签列表
     *
     * @param categoryLabelList 维度标签列表
     */
    void setCategoryLabelList(List<String> categoryLabelList);
}
