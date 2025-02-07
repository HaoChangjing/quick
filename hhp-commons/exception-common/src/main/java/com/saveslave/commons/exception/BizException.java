package com.saveslave.commons.exception;

import cn.hutool.core.util.StrUtil;
import com.saveslave.commons.contanst.ReturnCode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 业务异常
 *
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class BizException extends RuntimeException {

    private static final long serialVersionUID = 4797334765857170688L;

    private Integer errCode;

    private Object data;

    /**
     * 兼容844接口
     */
    private Object errDatas;

    public BizException() {}

    public BizException(String errMsg) {
        super(errMsg);
    }

    public BizException(Throwable throwable) {
        super(throwable);
    }

    public BizException(String errMsgTemplate, Object... args) {
        this(StrUtil.format(errMsgTemplate, args));
    }

    public BizException(String errMsg, Throwable throwable) {
        super(errMsg, throwable);
    }

    public BizException(String errMsgTemplate, Throwable throwable, Object... args) {
        this(StrUtil.format(errMsgTemplate, args), throwable);
    }

    public BizException(Object data, Integer errCode, String errMsg) {
        this(errCode, errMsg);
        this.data = data;
    }

    public BizException(Object data, Integer errCode, String errMsgTemplate, Object... args) {
        this(errCode, errMsgTemplate, args);
        this.data = data;
    }

    public BizException(Integer errCode, String errMsg) {
        this(errMsg);
        this.errCode = errCode;
    }

    public BizException(Integer errCode, String errMsgTemplate, Object... args) {
        this(errCode, StrUtil.format(errMsgTemplate, args));
    }

    public BizException(Integer errCode, Throwable throwable) {
        this(throwable);
        this.errCode = errCode;
    }

    public BizException(Integer errCode, String errMsg, Throwable throwable) {
        this(errMsg, throwable);
        this.errCode = errCode;
    }

    public BizException(ExceptionCode exceptionCode, String errMsg) {
        this(exceptionCode.getCode(), errMsg);
    }

    public BizException(ExceptionCode exceptionCode) {
        this(exceptionCode.getCode(), exceptionCode.getDefaultMessage());
    }

    public BizException(ExceptionCode exceptionCode, Throwable throwable) {
        this(exceptionCode.getCode(), exceptionCode.getDefaultMessage(), throwable);
    }


    public BizException(ReturnCode returnCode) {
        this(returnCode.getCode(), returnCode.getName());
    }

    public BizException(ReturnCode returnCode, String errMsg) {
        this(returnCode.getCode(), errMsg);
    }

    public BizException(ReturnCode returnCode, Object data) {
        this(returnCode.getCode(), returnCode.getName(), data);
    }

    public BizException(ReturnCode returnCode, Throwable throwable) {
        this(returnCode.getCode(), returnCode.getName(), throwable);
    }

}
