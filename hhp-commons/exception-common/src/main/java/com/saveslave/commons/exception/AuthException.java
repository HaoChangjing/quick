package com.saveslave.commons.exception;

/**
 * 登录异常
 *
 */
public class AuthException extends RuntimeException {

    private static final long serialVersionUID = -3389341299224547474L;

    private Integer errCode;

    public AuthException() {
    }

    public AuthException(String errMsg) {
        super(errMsg);
    }

    public AuthException(Throwable throwable) {
        super(throwable);
    }

    public AuthException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public AuthException(int errCode, String msg) {
        super(msg);
        this.errCode = errCode;
    }

    public AuthException(int errCode, Throwable throwable) {
        super(throwable);
        this.errCode = errCode;
    }

    public AuthException(int errCode, String msg, Throwable throwable) {
        super(msg, throwable);
        this.errCode = errCode;
    }

    public Integer getErrCode() {
        return errCode;
    }
}
