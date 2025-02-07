package com.saveslave.commons.exception;

/**
 * Excel处理异常
 *
 */
public class ExcelException extends RuntimeException {

    private static final long serialVersionUID = -3389341299224547474L;

    private Integer errCode;

    public ExcelException() {
    }

    public ExcelException(String errMsg) {
        super(errMsg);
    }

    public ExcelException(Throwable throwable) {
        super(throwable);
    }

    public ExcelException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public ExcelException(int errCode, String msg) {
        super(msg);
        this.errCode = errCode;
    }

    public ExcelException(int errCode, Throwable throwable) {
        super(throwable);
        this.errCode = errCode;
    }

    public ExcelException(int errCode, String msg, Throwable throwable) {
        super(msg, throwable);
        this.errCode = errCode;
    }

    public Integer getErrCode() {
        return errCode;
    }
}
