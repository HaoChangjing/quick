package com.saveslave.commons.contanst;

/**
 * 协议格式枚举
 */
public enum FormatEnum {

    JSON("json"), 
    XML("xml"),;

    String type;

    FormatEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
