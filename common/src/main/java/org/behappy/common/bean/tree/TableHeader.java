package org.behappy.common.bean.tree;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author songyide
 * @date 2022/8/29
 */
@ApiModel("表头")
public record TableHeader(@ApiModelProperty("列信息") List<TreeItem> items) {
    public static TableHeader of(List<TreeItem> items) {
        return new TableHeader(items);
    }
}
