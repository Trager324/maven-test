package com.syd.common.bean;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * @author songyide
 * @date 2022/9/5
 */
public interface IPageParam {
    int DEF_CURRENT = 1;
    int DEF_SIZE = 10;

    /**
     * 页码
     *
     * @return 页码
     */
    Integer getCurrent();

    /**
     * 页大小
     *
     * @return 页大小
     */
    Integer getSize();

    /**
     * 排序字段
     *
     * @return 排序字段
     */
    List<OrderItem> getOrders();

    /**
     * 转化成mybatis plus中的Page
     *
     * @return 分页参数
     * @see IPage
     */
    default <T> IPage<T> newPage() {
        if (getCurrent() == null || getSize() == null) {
            return null;
        }
        Page<T> page = new Page<>(getCurrent(), getSize());
        page.setOrders(getOrders());
        return page;
    }

    /**
     * 分页结果
     *
     * @param list 列表
     * @return 分页结果
     */
    default <T> PageResult<T> paging(List<T> list) {
        return PageResult.paging(list, this);
    }
}
