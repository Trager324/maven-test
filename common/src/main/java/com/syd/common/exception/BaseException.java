package com.syd.common.exception;

import com.syd.common.constant.ResponseCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 基础异常
 *
 * @author songyide
 * @date 2022/8/29
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract sealed class BaseException extends RuntimeException permits BusinessException, SystemException, ThirdPartyException {
    private static final String PREFIX_BUSINESS = ResponseCode.A0001.getCode().substring(0, 1);
    private static final String PREFIX_THIRD_PARTY = ResponseCode.C0001.getCode().substring(0, 1);
    /**
     * 错误码
     */
    private ResponseCode code = ResponseCode.B0001;
    /**
     * http响应码
     */
    private Integer httpStatus = HttpStatus.OK.value();
    /**
     * 提示用户做出正确操作
     */
    private String detailMessage;
    /**
     * 用户端错误明细: 提示用户做出正确操作
     * 系统端错误明细: 内部调试信息便于排查问题
     */
    private String debugInfo;

    protected BaseException(ResponseCode code) {
        this.code = code;
    }

    /**
     * 异常工厂，返回相应异常
     *
     * @param code 返回码
     * @return 需要的抛出异常
     */
    public static BaseException of(ResponseCode code) {
        if (code.getCode().startsWith(PREFIX_BUSINESS)) {
            return new BusinessException(code);
        } else if (code.getCode().startsWith(PREFIX_THIRD_PARTY)) {
            return new ThirdPartyException(code);
        }
        return new SystemException(code);
    }

    /**
     * 异常工厂，返回相应异常
     *
     * @param code          返回码
     * @param detailMessage 详细异常信息以便排查
     * @return 需要的抛出异常
     */
    public static BaseException of(ResponseCode code, String detailMessage) {
        return of(code).setDetailMessage(detailMessage);
    }

    /**
     * 异常工厂，返回相应异常
     *
     * @param code  返回码
     * @param cause 引发原因
     * @return 需要的抛出异常
     */
    public static BaseException of(ResponseCode code, Throwable cause) {
        return of(code).initCause(cause);
    }

    /**
     * 异常工厂，返回相应异常
     *
     * @param code          返回码
     * @param detailMessage 详细异常信息以便排查
     * @param cause         引发原因
     * @return 需要的抛出异常
     */
    public static BaseException of(ResponseCode code, String detailMessage, Throwable cause) {
        return of(code, cause).setDetailMessage(detailMessage);
    }

    public String getDetailMessage() {
        if (detailMessage == null) {
            if (getCause() instanceof BaseException) {
                // 嵌套异常路径压缩
                detailMessage = ((BaseException)getCause()).getDetailMessage();
            }
        }
        return detailMessage;
    }

    public BaseException setDetailMessage(String detailMessage) {
        this.detailMessage = detailMessage;
        return this;
    }

    public String getDebugInfo() {
        if (debugInfo == null) {
            if (getCause() instanceof BaseException) {
                // 嵌套异常路径压缩
                debugInfo = ((BaseException)getCause()).getDebugInfo();
            }
        }
        return debugInfo;
    }

    public BaseException setDebugInfo(String debugInfo) {
        this.debugInfo = debugInfo;
        return this;
    }

    public BaseException setHttpStatus(HttpStatus status) {
        this.httpStatus = status.value();
        return this;
    }

    @Override
    public BaseException initCause(Throwable cause) {
        super.initCause(cause);
        return this;
    }

    @Override
    public String getMessage() {
        String info = code.getMessage();
        if (getDetailMessage() != null) {
            info += ": " + detailMessage;
        }
        return info;
    }
}
