package com.syd.common.config;

import com.syd.common.bean.Response;
import com.syd.common.constant.ResponseCode;
import com.syd.common.exception.BaseException;
import com.syd.common.exception.ThirdPartyException;
import com.syd.common.util.ExtStrUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.Objects;

/**
 * 全局异常处理器
 *
 * @author ruoyi
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 是否向前端返回报错信息，与swagger开启策略相同
     */
    @Value("${springfox.documentation.enabled}")
    private boolean enabled;

    private String withDebugInfo(String detailMessage, String debugInfo) {
        if (!enabled || ExtStrUtils.isEmpty(debugInfo)) {
            return detailMessage;
        }
        return String.join("; debugInfo: ", detailMessage, debugInfo);
    }

    /**
     * 权限校验异常
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AccessDeniedException.class)
    public Response<?> handleAccessDeniedException(AccessDeniedException e, HttpServletRequest req) {
        return Response.response(ResponseCode.A0300, req.getRequestURI() + " - " + e.getMessage());
    }

    /**
     * 统一异常
     */
    @ExceptionHandler(BaseException.class)
    public Response<?> handleBaseException(BaseException e, HttpServletResponse rsp) {
        if (e.getDebugInfo() != null) {
            if (e instanceof ThirdPartyException) {
                log.error("三方服务出错！" + e.getDebugInfo());
            } else {
                log.debug(e.getDebugInfo());
            }
        }
        log.error(e.getMessage(), ExtStrUtils.ifNull(e.getCause(), e));
        rsp.setStatus(e.getHttpStatus());
        return Response.response(e.getCode(), withDebugInfo(e.getDetailMessage(), e.getDebugInfo()));
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(BindException.class)
    public Response<?> handleBindException(BindException e) {
        log.error(e.getMessage(), e);
        String message = e.getAllErrors().get(0).getDefaultMessage();
        return Response.response(ResponseCode.A0400, message);
    }

    /**
     * 自定义验证异常
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        var fieldError = Objects.requireNonNull(e.getBindingResult().getFieldError());
        String message = fieldError.getField() + fieldError.getDefaultMessage();
        return Response.response(ResponseCode.A0400, message);
    }

    /**
     * 反序列化异常
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Response<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error(e.getMessage(), e);
        return Response.response(ResponseCode.A0400, withDebugInfo(null, e.getMessage()));
    }

    /**
     * 请求方式不支持
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Response<?> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException e, HttpServletRequest req) {
        String message = String.format("请求地址'%s'不支持'%s'请求", req.getRequestURI(), e.getMethod());
        log.error(message);
        return Response.response(ResponseCode.A0400, message);
    }

    /**
     * 拦截未知的运行时异常
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public Response<?> handleRuntimeException(RuntimeException e, HttpServletRequest req) {
        String uri = req.getRequestURI();
        log.error("请求地址'{}',发生未知异常.", uri, e);
        return Response.response(ResponseCode.B0001, withDebugInfo("", e.getMessage()));
    }

    /**
     * 未知系统异常
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public Response<?> handleThrowable(Throwable e, HttpServletRequest req) {
        String uri = req.getRequestURI();
        log.error("请求地址'{}',发生系统异常.", uri, e);
        return Response.response(ResponseCode.B0001, withDebugInfo("", e.getMessage()));
    }
}
