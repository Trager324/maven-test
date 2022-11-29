package com.syd.common.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.syd.common.constant.ResponseCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

/**
 * @author songyide
 * @date 2022/5/10
 */
@Data(staticConstructor = "of")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ApiModel("统一返回结果")
public class Response<T> {

    @ApiModelProperty("状态码")
    private final String code;
    @ApiModelProperty("消息")
    private final String message;
    @ApiModelProperty("数据")
    private final T data;
    @ApiModelProperty("额外信息")
    private final Map<String, Object> extra;

    /**
     * 返回成功消息
     */
    public static <T> Response<T> success() {
        return success(null);
    }

    /**
     * 返回成功消息
     */
    public static <T> Response<T> success(T data) {
        return response(ResponseCode.OK, data);
    }

    /**
     * 返回消息
     *
     * @param code 状态码
     * @return 警告消息
     */
    public static <T> Response<T> response(ResponseCode code) {
        return response(code, null);
    }

    /**
     * 返回消息
     *
     * @param code 状态码
     * @return 警告消息
     */
    public static <T> Response<T> response(ResponseCode code, T data) {
        return response(code.getCode(), code.getMessage(), data, null);
    }

    /**
     * 返回消息
     *
     * @param code    状态码
     * @param message 返回内容
     * @param data    数据
     * @param extra   额外信息
     * @return 警告消息
     */
    public static <T> Response<T> response(String code, String message, T data, Map<String, Object> extra) {
        return new Response<>(code, message, data, extra);
    }
}