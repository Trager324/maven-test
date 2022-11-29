package com.syd.common.exception;

import com.syd.common.constant.ResponseCode;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 第三方服务异常，响应码以C开头
 *
 * @author songyide
 * @date 2022/10/25
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ThirdPartyException extends BaseException {
    protected ThirdPartyException(ResponseCode code) {
        super(code);
        super.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
