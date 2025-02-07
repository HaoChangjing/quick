package com.saveslave.commons.exception;

/**
 * @Description: 幂等异常
 * @ClassName IdempotentException
 **/
public class IdempotentException extends RuntimeException{

    private static final long serialVersionUID = -5996307250816774399L;

    private Integer errCode;

    public IdempotentException() {
    }

    public IdempotentException(String errMsg) {
        super(errMsg);
    }

    public IdempotentException(Throwable throwable) {
        super(throwable);
    }

    public IdempotentException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public IdempotentException(int errCode, String msg) {
        super(msg);
        this.errCode = errCode;
    }

    public IdempotentException(int errCode, Throwable throwable) {
        super(throwable);
        this.errCode = errCode;
    }

    public IdempotentException(int errCode, String msg, Throwable throwable) {
        super(msg, throwable);
        this.errCode = errCode;
    }

    public Integer getErrCode() {
        return errCode;
    }
}
