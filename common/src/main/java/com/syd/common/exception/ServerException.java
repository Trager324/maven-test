package com.syd.common.exception;

import com.syd.common.constant.ResponseCode;
import lombok.Getter;

/**
 * 服务端异常，响应码以B开头
 *
 * @author songyide
 * @date 2022/8/29
 */
@Getter
public non-sealed class ServerException extends BaseException {
    protected ServerException(String message, ResponseCode code) {
        super(message, code);
        // 服务端异常默认500
//        super.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
