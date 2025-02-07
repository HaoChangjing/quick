package com.saveslave.commons.contanst;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ClientCode implements IBaseEnum<String, String> {

    PC("1", "网页端"),
    APP("2", "微信小程序"),
    PDA("3", "PDA端"),
    CODE_CLIENT("8", "发码客户端")
    ;

    private final String code;

    private final String name;

}
