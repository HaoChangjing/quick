package com.saveslave.commons.biz.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 邮箱配置表
 * </p>
 *
 */
@Data
@Accessors(chain = true)
public class MailDeployDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long pId;

    /**
     * 传输协议（smtp,imap）
     */
    private String transportProtocol;

    /**
     * 服务器地址
     */
    private String serverAddress;

    /**
     * 服务器端口
     */
    private Integer port;

    /**
     * 发件人邮箱
     */
    private String mailbox;

    /**
     * 发件人账户密码
     */
    private String mailboxPwd;

    /**
     * 安全连接 0不开启 1开启
     */
    private Integer secureConnection;

    /**
     * 发送人
     */
    private String despatcher;
}
