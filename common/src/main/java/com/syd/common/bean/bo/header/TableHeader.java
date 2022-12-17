package com.syd.common.bean.bo.header;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author songyide
 * @date 2022/8/29
 */
@ApiModel("表头")
public record TableHeader(@ApiModelProperty("列信息") List<HeaderItem> items) {
    public static TableHeader of(List<HeaderItem> items) {
        return new TableHeader(items);
    }
}
