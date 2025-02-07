package com.saveslave.commons.exception;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @ClassName ErrorResult
 **/
@Data
@Accessors(chain = true)
public class ErrorResult {

    private Integer code;
    private String msg;
    private Object data;
    /**
     * 兼容844接口
     */
    private Object errDatas;
    private Long timestamp;
}
