package com.saveslave.commons.exception;

import cn.hutool.core.exceptions.ValidateException;
import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.databind.JsonMappingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.nio.file.AccessDeniedException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;

/**
 * 异常通用处理
 *
 */
@Slf4j
@ControllerAdvice
@ResponseBody
public class DefaultExceptionAdvice {

    public HttpServletResponse getResponse() {
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            return attributes.getResponse();
        }
        return null;
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ErrorResult badRequestException(IllegalArgumentException e) {
        return defErrHandler(HttpStatus.INTERNAL_SERVER_ERROR.value(), "参数解析失败", e);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ErrorResult badMethodExpressException(AccessDeniedException e) {
        return defWarnHandler(HttpStatus.FORBIDDEN.value(), "没有权限请求当前方法", e);
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ErrorResult handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return defWarnHandler(HttpStatus.METHOD_NOT_ALLOWED.value(),"不支持当前请求方法", e);
    }

    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    public ErrorResult handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        return defWarnHandler(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(),"不支持当前媒体类型", e);
    }

    @ExceptionHandler({SQLException.class})
    public ErrorResult handleSQLException(SQLException e) {
        return defErrHandler(HttpStatus.INTERNAL_SERVER_ERROR.value(),"服务运行SQLException异常", e);
    }

    @ExceptionHandler(IdempotentException.class)
    public ErrorResult handleException(IdempotentException e) {
        int errCode = ObjectUtil.isNotEmpty(e.getErrCode())?e.getErrCode():HttpStatus.INTERNAL_SERVER_ERROR.value();
        return defWarnHandler(errCode,e.getMessage(), e);
    }

    @ExceptionHandler(Exception.class)
    public ErrorResult handleException(Exception e) {
        return defErrHandler(HttpStatus.INTERNAL_SERVER_ERROR.value(),"网络连接失败", e);
    }

    @ExceptionHandler(ValidateException.class)
    public ErrorResult handleException(ValidateException e) {
        return defWarnHandler(ObjectUtil.isNotNull(e.getStatus())?e.getStatus():HttpStatus.INTERNAL_SERVER_ERROR.value(),e.getMessage(), e);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(BizException.class)
    public ErrorResult handleException(BizException e) {
        Integer errCode = e.getErrCode() != null ? e.getErrCode() : HttpStatus.INTERNAL_SERVER_ERROR.value();
        return defWarnHandlerByData(e.getData(), e.getErrDatas(), errCode, e.getMessage(), e);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthException.class)
    public ErrorResult handleException(AuthException e) {
        Integer errCode = e.getErrCode() != null ? e.getErrCode() : HttpStatus.INTERNAL_SERVER_ERROR.value();
        return defWarnHandler(errCode, e.getMessage(), e);
    }

    @ExceptionHandler(BindException.class)
    public ErrorResult handleException(BindException e) {
        String message = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(","));
        return defErrHandler(HttpStatus.INTERNAL_SERVER_ERROR.value(),message, e);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResult handleException(MethodArgumentNotValidException e) {
        return defErrHandler(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getBindingResult().getFieldError().getDefaultMessage(), e);
    }

    @ExceptionHandler(LockException.class)
    public ErrorResult handleException(LockException e) {
        int errCode = ObjectUtil.isNotEmpty(e.getErrCode()) ? e.getErrCode() : HttpStatus.INTERNAL_SERVER_ERROR.value();
        return defErrHandler(errCode, e.getMessage(), e);
    }

    @ExceptionHandler(ExcelException.class)
    public ErrorResult handleException(ExcelException e) {
        int errCode = ObjectUtil.isNotEmpty(e.getErrCode()) ? e.getErrCode() : HttpStatus.INTERNAL_SERVER_ERROR.value();
        return defErrHandler(errCode, e.getMessage(), e);
    }

    @ExceptionHandler(CompletionException.class)
    public ErrorResult handleException(CompletionException e) {
        Throwable cause = e.getCause();
        return defErrHandler(HttpStatus.INTERNAL_SERVER_ERROR.value(), cause.getMessage(), e);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorResult handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        String erroMessage = null;
        Throwable rootCause = e.getRootCause();
        if (rootCause instanceof JsonMappingException) {
            erroMessage = getDataBindErrorTips(((JsonMappingException) rootCause).getPath());
        }
        if (erroMessage == null) {
            erroMessage = "参数解析失败";
        }
        return defInfoHandler(HttpStatus.INTERNAL_SERVER_ERROR.value(), erroMessage, e);
    }

    private ErrorResult defErrHandler(Integer errCode, String errMsg, Exception e) {
        HttpServletResponse response = getResponse();
        if (response != null) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        log.error(errMsg, e);
        return new ErrorResult().setCode(errCode).setMsg(errMsg).setTimestamp(System.currentTimeMillis());
    }

    private ErrorResult defWarnHandler(Integer errCode,String errMsg, Exception e) {
        HttpServletResponse response = getResponse();
        if (response != null) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        log.warn(errMsg, e);
        return new ErrorResult().setCode(errCode).setMsg(errMsg).setTimestamp(System.currentTimeMillis());
    }
    private ErrorResult defWarnHandlerByData(Object data, Object errorDatas, Integer errCode, String errMsg, Exception e) {
        HttpServletResponse response = getResponse();
        if (response != null) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        log.warn(errMsg, e);
        return new ErrorResult().setData(data).setErrDatas(errorDatas).setCode(errCode).setMsg(errMsg).setTimestamp(System.currentTimeMillis());
    }

    private ErrorResult defInfoHandler(Integer errCode, String errMsg, Exception e) {
        HttpServletResponse response = getResponse();
        if (response != null) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        log.info(errMsg, e);
        return new ErrorResult().setCode(errCode).setMsg(errMsg).setTimestamp(System.currentTimeMillis());
    }

    private String getDataBindErrorTips(List<JsonMappingException.Reference> path) {
        if (CollectionUtils.isEmpty(path)) {
            return null;
        }
        for (int i = path.size() - 1; i >= 0; i--) {
            JsonMappingException.Reference reference = path.get(i);
            if (reference == null) {
                continue;
            }
            Object target = reference.getFrom();
            if (target == null) {
                continue;
            }
            String result = this.getDataBindErrorTips(target.getClass(), reference.getFieldName());
            if (!StringUtils.isEmpty(result)) {
                return result;
            }
        }
        return null;
    }

    private String getDataBindErrorTips(Class<?> targetCalss, String fieldName) {
        if (targetCalss == null || StringUtils.isBlank(fieldName)) {
            return null;
        }
        while (targetCalss != Object.class) {
            Field field;
            try {
                field = targetCalss.getDeclaredField(fieldName);
                DataBindErrorTips dataBindErrorTips = field.getDeclaredAnnotation(DataBindErrorTips.class);
                return dataBindErrorTips == null ? null : dataBindErrorTips.value();
            } catch (NoSuchFieldException e) {
                targetCalss = targetCalss.getSuperclass();
            }
        }
        return null;
    }
}
