package com.syd.common.exception;

import com.syd.common.constant.ResponseCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 业务异常，响应码以A开头
 *
 * @author songyide
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public non-sealed class BusinessException extends BaseException {
    protected BusinessException(ResponseCode code) {
        super(code);
    }
}
