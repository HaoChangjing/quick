package com.saveslave.commons.util;

import com.saveslave.commons.contanst.ReturnCode;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 *
 * @param <T>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable {

    private T data;
    private Integer code;
    private String msg;

    public static <T> Result<T> succeed(String msg) {
        return succeedWith(null, ReturnCode.SUCCESS.getCode(), msg);
    }

    public static <T> Result<T> succeed(T model, String msg) {
        return succeedWith(model, ReturnCode.SUCCESS.getCode(), msg);
    }

    public static <T> Result<T> succeed(T model) {
        return succeedWith(model, ReturnCode.SUCCESS.getCode(), "");
    }

    public static <T> Result<T> succeedWith(T datas, Integer code, String msg) {
        return new Result<>(datas, code, msg);
    }

    public static <T> Result<T> failed(String msg) {
        return failedWith(null, ReturnCode.ERROR.getCode(), msg);
    }

    public static <T> Result<T> failed(T model, String msg) {
        return failedWith(model, ReturnCode.ERROR.getCode(), msg);
    }

    public static <T> Result<T> failedWith(T datas, Integer code, String msg) {
        return new Result<>(datas, code, msg);
    }

    public static <T> Result<T> result(ReturnCode returnCode) {
        return new Result<>(null, returnCode.getCode(), returnCode.getName());
    }

    public static <T> Result<T> resultData(T date, ReturnCode returnReturnCode) {
        return new Result<>(date, returnReturnCode.getCode(), returnReturnCode.getName());
    }

    public static <T> Result<T> resultData(T data, String msg) {
        return new Result<>(data, ReturnCode.SUCCESS.getCode(),msg );
    }

}
