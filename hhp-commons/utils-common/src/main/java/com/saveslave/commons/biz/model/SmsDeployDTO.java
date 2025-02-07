package com.saveslave.commons.biz.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 短信配置表
 * </p>
 *
 */
@Data
@Accessors(chain = true)
public class SmsDeployDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long pId;

    /**
     * 秘钥
     */
    private String accessKeyId;

    /**
     * 秘钥secret
     */
    private String accessKeySecret;

    /**
     * 阿里云短信模板code
     */
    private String templateCode;

    /**
     * 模板名称
     */
    private String templateName;

    /**
     * 模板内容
     */
    private String templateContent;

    /**
     * 1.金山云 2.阿里云
     */
    private Integer serviceProviderType;

    /**
     * 企业名称
     */
    private String companyName;

    /**
     * 短信签名
     */
    private String smsSign;

    /**
     * Token
     */
    private String token;

    /**
     * 开关  1:开  0:关
     */
    private Integer onOff;
}
