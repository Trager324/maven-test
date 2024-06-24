package org.behappy.common.exception;

import org.behappy.common.constant.ResponseCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.io.Serial;

/**
 * 基础异常
 *
 * @author songyide
 * @date 2022/8/29
 */
@Slf4j
public sealed abstract class BaseException extends RuntimeException
        permits ClientException, ServerException, ThirdPartyException {
    private static final String PREFIX_CLIENT = "A";
    private static final String PREFIX_SERVER = "B";
    private static final String PREFIX_THIRD_PARTY = "C";
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 错误码
     */
    @Getter
    private final ResponseCode code;
    /**
     * http响应码
     */
    @Getter
    private HttpStatus httpStatus = HttpStatus.OK;
    /**
     * 服务端错误明细: 内部调试信息便于排查问题
     */
    private StringBuilder debugInfoBuilder;

    /**
     * 异常基类构造器
     *
     * @param message 提示用户做出正确操作
     * @param code    响应消息
     */
    protected BaseException(String message, ResponseCode code) {
        super(message);
        this.code = code;
    }

    /**
     * 异常工厂，返回相应异常
     *
     * @param code 返回码
     * @return 需要的抛出异常
     */
    public static BaseException of(ResponseCode code) {
        return of(code, (String)null);
    }

    /**
     * 异常工厂，返回相应异常
     *
     * @param code    返回码
     * @param message 详细异常信息以便排查
     * @return 需要的抛出异常
     */
    public static BaseException of(ResponseCode code, String message) {
        var prefix = code.getCode().substring(0, 1);
        return switch (prefix) {
            case PREFIX_CLIENT -> new ClientException(message, code);
            case PREFIX_SERVER -> new ServerException(message, code);
            case PREFIX_THIRD_PARTY -> new ThirdPartyException(message, code);
            default -> {
                // Unrecognized response code, critical error here
                log.error("非法code" + code);
                yield new ServerException(null, code).appendDebugInfo("非法code" + code);
            }
        };
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
     * @param code    返回码
     * @param message 详细异常信息以便排查
     * @param cause   引发原因
     * @return 需要的抛出异常
     */
    public static BaseException of(ResponseCode code, String message, Throwable cause) {
        return of(code, message).initCause(cause);
    }

    public String getMessage() {
        var message = super.getMessage();
        if (message == null) {
            if (getCause() instanceof BaseException) {
                // 嵌套异常递归查询
                return getCause().getMessage();
            } else {
                // 返回默认消息
                message = code.getMessage();
            }
        }
        return message;
    }

    public String getDebugInfo() {
        if (debugInfoBuilder == null) {
            if (getCause() instanceof BaseException cause) {
                // 嵌套异常路径压缩
                appendDebugInfo(cause.getDebugInfo());
            }
        }
        @SuppressWarnings("all")
        var res = debugInfoBuilder.toString();
        return res;
    }

    public BaseException appendDebugInfo(String debugInfo) {
        if (this.debugInfoBuilder == null) {
            this.debugInfoBuilder = new StringBuilder();
        }
        this.debugInfoBuilder.append(debugInfo);
        return this;
    }

    @SuppressWarnings("all")
    public BaseException setHttpStatus(HttpStatus status) {
        this.httpStatus = status;
        return this;
    }

    @Override
    public BaseException initCause(Throwable cause) {
        super.initCause(cause);
        return this;
    }
}
