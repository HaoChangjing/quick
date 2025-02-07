package com.saveslave.commons.exception;

/**
 * @Description //分布式锁异常
 **/
public class LockException extends RuntimeException {

    private static final long serialVersionUID = -3389341299224547474L;

    private Integer errCode;

    public LockException() {
    }

    public LockException(String errMsg) {
        super(errMsg);
    }

    public LockException(Throwable throwable) {
        super(throwable);
    }

    public LockException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public LockException(int errCode, String msg) {
        super(msg);
        this.errCode = errCode;
    }

    public LockException(int errCode, Throwable throwable) {
        super(throwable);
        this.errCode = errCode;
    }

    public LockException(int errCode, String msg, Throwable throwable) {
        super(msg, throwable);
        this.errCode = errCode;
    }

    public Integer getErrCode() {
        return errCode;
    }
}
