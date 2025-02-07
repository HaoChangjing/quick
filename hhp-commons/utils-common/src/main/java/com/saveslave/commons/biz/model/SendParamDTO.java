package com.saveslave.commons.biz.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 发送邮件/短信参数
 */
@Data
@Accessors(chain = true)
public class SendParamDTO {

    /**
     * 收件地址
     */
    private String consigneeAddress;

    /**
     * 标题(邮件必填，短信可不填)
     */
    private String title;

    /**
     * 标题(邮件不填，短信必填)
     */
    private String outsideCode;

    /**
     * 内容(邮件必填，)
     */
    private String content;

}