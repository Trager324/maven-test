package org.behappy.common.exception;

import org.behappy.common.constant.ResponseCode;
import lombok.Getter;

/**
 * 用户端异常，响应码以A开头
 *
 * @author songyide
 */
@Getter
public non-sealed class ClientException extends BaseException {
    protected ClientException(String message, ResponseCode code) {
        super(message, code);
        // 用户端异常默认400
//        setHttpStatus(HttpStatus.BAD_REQUEST);
    }
}
