package org.behappy.common.bean.tree;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 台账返回表单对象，包含表头数据和表格数据
 *
 * @author songyide
 * @date 2022/8/29
 */
@ApiModel("动态表头返回值")
public record TableHeaderData<T>(@ApiModelProperty("表头") TableHeader header, @ApiModelProperty("数据") T data) {
    public static <T> TableHeaderData<T> of(TableHeader header, T data) {
        return new TableHeaderData<>(header, data);
    }
}
