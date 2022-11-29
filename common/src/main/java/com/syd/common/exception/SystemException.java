package com.syd.common.exception;

import com.syd.common.constant.ResponseCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 系统异常，响应码以B开头
 *
 * @author songyide
 * @date 2022/8/29
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SystemException extends BaseException {
    protected SystemException(ResponseCode code) {
        super(code);
        super.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
