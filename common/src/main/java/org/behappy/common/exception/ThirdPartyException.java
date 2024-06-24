package org.behappy.common.exception;

import org.behappy.common.constant.ResponseCode;
import org.springframework.http.HttpStatus;

/**
 * 第三方服务异常，响应码以C开头
 * <p>TODO: 三方异常可通过邮件报警
 *
 * @author songyide
 * @date 2022/10/25
 */
public non-sealed class ThirdPartyException extends BaseException {
    protected ThirdPartyException(String message, ResponseCode code) {
        super(message, code);
        super.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
