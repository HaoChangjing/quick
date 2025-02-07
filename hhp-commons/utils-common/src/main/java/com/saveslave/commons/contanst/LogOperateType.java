package com.saveslave.commons.contanst;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 日志操作类型
 *
 */
@Getter
@AllArgsConstructor
public enum LogOperateType {
    /**
     * 操作类型
     */
    UNKNOWN(0,"unknown"),
    DELETE(1,"delete"),
    SELECT(2,"select"),
    UPDATE(3,"update"),
    INSERT(4,"insert");

    private final int operateTypeCode;

    private final String operateTypeVal;

}
