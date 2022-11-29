package com.syd.common.bean;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Stream;

/**
 * 表格分页数据对象，减少数据传输量
 *
 * @author songyide
 */
@Data(staticConstructor = "of")
@ApiModel(value = "分页返回结果")
public class PageResult<T> {
    @ApiModelProperty(value = "列表数据")
    private final List<T> records;
    @ApiModelProperty(value = "数据总数")
    private final long total;

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
     * @param list    列表数据
     * @param current 页码
     * @param size    页大小
     */
    public static <T> PageResult<T> paging(List<T> list, int current, int size) {
        return new PageResult<>(pageReduce(list, current, size), list.size());
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
