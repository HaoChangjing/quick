package com.saveslave.commons.contanst;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户类型枚举
 *
 */
@Getter
@AllArgsConstructor
public enum UserType implements IBaseEnum<String, String> {

    /**
     * 企业
     */
    COMPANY("company", "企业用户"),
    /**
     * 经销商
     */
    AGENT("agent", "经销商用户"),
    /**
     * 供应商
     */
    SUPPLIER("supplier", "供应商用户");

    private final String code;

    private final String name;

}
