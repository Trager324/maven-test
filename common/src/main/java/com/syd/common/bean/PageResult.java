package com.syd.common.bean;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 表格分页数据对象，减少数据传输量
 *
 * @author songyide
 */
@ApiModel(value = "分页返回结果")
public record PageResult<T>(@ApiModelProperty(value = "列表数据") List<T> records,
                            @ApiModelProperty(value = "数据总数") long total) {
    /**
     * 分页
     *
     * @param page mybatis-plus分页结果
     * @return 分页结果
     */
    public static <T> PageResult<T> of(IPage<T> page) {
        return new PageResult<>(page.getRecords(), page.getTotal());
    }

    /**
     * 逻辑分页
     *
     * @param list 列表数据
     * @param page 分页参数
     */
    public static <T> PageResult<T> paging(List<T> list, IPageParam page) {
        return new PageResult<>(pageReduce(list, page.getCurrent(), page.getSize()), list.size());
    }

    public static <T> List<T> pageReduce(List<T> list, int current, int size) {
        // 计算页开始和结束下标
        int start = (current - 1) * size, end = start + size;
        // subList分页并防止下标越界
        return list.subList(Math.min(start, list.size()), Math.min(end, list.size()));
    }
}
