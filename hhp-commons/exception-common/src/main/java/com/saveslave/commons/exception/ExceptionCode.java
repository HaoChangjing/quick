package com.saveslave.commons.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {
    /**/
    ILLEGAL_STATE(4001, "非法访问"),
    PARAM_REQUIRED(4002, "参数不能为空"),
    PARAM_FORMAT_ILLEGAL(4003, "参数格式错误"),
    REQUEST_DATA_DUPLICATION(4004, "重复请求"),
    REQUEST_DATA_ERROR(4005, "请求数据错误"),
    REQUEST_DATA_NOT_MATCH(4006, "请求数据不一致"),
    DATA_RESTRICT(4007, "数据包超过限制"),
    PERMISSION_DENIED(4008, "权限不足"),
    RECORD_NOT_EXIST(5001, "记录不存在"),
    RECORD_EXISTED(5002, "记录已存在"),
    RECORD_ILLEGAL_STATE(5003, "数据异常"),
    BALANCE_NOT_ENOUGH(5103, "余额不足"),
    CALL_INNER_ERROR(5800, "调用内部服务接口异常"),
    THIRD_PART_ERROR(5801, "调用第三方接口异常"),
    SYSTEM_ERROR(9999, "系统异常"),
    SIGN_NOT_VALID(6001, "Sign无效"),
    SIGN_EXP(6002, "Sign已过期"),
    SIGN_NOT_NULL(6003, "Sign不能为空"),
    SIGN_CHECK_PARAM(6004,"请检查授权配置是否正确"),
    VALID_CHECK_PARAM(6005,"参数校验错误提示"),
    VALID_CHECK_MARK_PARAM(6006,"数码逻辑判断"),
    TOKEN_EXCEPTION(6007,"Token解析异常"),
    PART_FAILED(7001, "部分失败"),
    ALL_FAILED(7002, "全部失败"),
    BIZ_FAILED(7003, "业务处理失败"),
    NET_ERROR(8001, "网络异常，请稍后重试");

    public final int code;
    public final String defaultMessage;

    ExceptionCode(int code, String defaultMessage) {
        this.code = code;
        this.defaultMessage = defaultMessage;
    }
}
