package com.saveslave.sysmanager.service.persistent.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author author
 * @since 2019-10-12
 */
@Data
@Accessors(chain = true)
@TableName("SYS_SMS_RESULT")
public class SmsResultDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "P_ID", type = IdType.ASSIGN_ID)
    private Long pId;

    /**
     * 短信发送执行结果
     */
    @TableField("RESULT")
    private Integer result;

    /**
     * 短信发送执行结果信息
     */
    @TableField("ERR_MSG")
    private String errMsg;

    /**
     * 短信发送的扩展信息
     */
    @TableField("EXT")
    private String ext;

    /**
     * 接收手机号，群发短信中间以","隔开
     */
    @TableField("PHONE_NUM")
    private String phoneNum;

    /**
     * 短信模板ID
     */
    @TableField("TEMPLATE_ID")
    private Integer templateId;

    /**
     * 短信模板参数
     */
    @TableField("PARAMS")
    private String params;

    /**
     * 短信发送时间
     */
    @TableField("CREATE_TIME")
    private String createTime;

    /**
     * 业务id
     */
    @TableField("BIZ_ID")
    private Long bizId;
    /**
     * 操作人
     */
    @TableField("creator")
    private String creator;


}
