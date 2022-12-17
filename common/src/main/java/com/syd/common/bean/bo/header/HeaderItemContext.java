package com.syd.common.bean.bo.header;

import java.util.List;

/**
 * @author songyide
 * @date 2022/12/13
 */
public interface HeaderItemContext {
    /**
     * 获取表头项
     *
     * @return 表头项
     */
    List<HeaderItem> getItems();

    /**
     * 设置表头项
     *
     * @param items 表头项
     */
    void setItems(List<HeaderItem> items);
}
