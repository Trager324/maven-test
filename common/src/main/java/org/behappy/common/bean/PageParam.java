package org.behappy.common.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * @author songyide
 * @date 2022/6/6
 */
@Data
@ApiModel("分页参数")
public class PageParam implements IPageParam {
    private static final int MAX_PAGE_SIZE = 500;
    @ApiModelProperty("查询页码")
    @Min(value = 1, message = "页码必须大于0")
    private Integer current = DEF_CURRENT;
    @ApiModelProperty("分页大小")
    @Max(value = MAX_PAGE_SIZE, message = "分页大小不能超过500")
    private Integer size = DEF_SIZE;
    @ApiModelProperty("排序列")
    private List<Sort.Order> orders;
}
