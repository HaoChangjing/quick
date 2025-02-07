package com.saveslave.commons.contanst;

/**
 * 数据状态枚举
 */
public enum DataState {

    /**
     * 未删除
     */
    UN_DELETED(1),
    /**
     * 供应商已删除
     */
    SUPPLIER_DELETED(2),
    /**
     * 已删除
     */
    DELETED(0),
    /**
     * 未激活、禁用
     */
    UN_ACTIVATE(0),
    /**
     * 已激活、启用
     */
    ACTIVATE(1),

    /**
     * 自制
     */
    SELF_CONTRO(1),
    /**
     * 接口
     */
    UN_SELF_CONTRO(2);
    private Integer code;

    DataState(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

}
