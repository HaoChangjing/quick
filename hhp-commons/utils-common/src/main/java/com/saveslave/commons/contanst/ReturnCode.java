package com.saveslave.commons.contanst;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 返回参数枚举类<br>
 * 对接外部系统返回用
 *
 */
@Getter
@AllArgsConstructor
public enum ReturnCode implements IBaseEnum<Integer, String> {

    /**
     * 成功
     */
    SUCCESS(200, "SUCCESS"),
    ERROR(1, "ERROR"),
    //请求参数相关
    PARAM_MISS(1001, "参数缺失"),
    PARAM_ERROR(1002, "参数错误"),
    CODE_EXIST(1003, "编码已存在"),
    DATA_RESTRICT(1004, "数据包超过限制"),
    ALL_FAILED(1005, "全部失败"),
    PORTION_FAILED(1006, "部分失败"),
    ALREADY_DOWNLOAD(1007, "已被下载"),
    //全局异常
    SERVER_ABNORMAL(2001, "服务器异常"),
    SERVER_ERROR(2002, "服务器错误"),
    NETWORK_ERROR(2003, "网络异常,请稍后重试"),
    HANDLE_FAIL(2004,"业务处理失败"),
    POWER_FAIL(2005,"权限不足"),
    
    ;

    /**
     * 返回码
     */
    private final Integer code;

    /**
     * 返回描述
     */
    private final String name;

}
