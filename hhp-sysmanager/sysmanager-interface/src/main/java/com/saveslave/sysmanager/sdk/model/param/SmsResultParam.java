package com.saveslave.sysmanager.sdk.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel("SmsResultParam")
public class SmsResultParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @ApiModelProperty(value = "主键ID")
    private Long pId;

    /**
     * 短信发送执行结果
     */
    @ApiModelProperty("短信发送执行结果")
    private Integer result;

    /**
     * 短信发送执行结果信息
     */
    @ApiModelProperty("短信发送执行结果信息")
    private String errMsg;

    /**
     * 短信发送的扩展信息
     */
    @ApiModelProperty("短信发送的扩展信息")
    private String ext;

    /**
     * 接收手机号，群发短信中间以","隔开
     */
    @ApiModelProperty("接收手机号，群发短信中间以\",\"隔开")
    private String phoneNum;

    /**
     * 短信模板ID
     */
    @ApiModelProperty("短信模板ID")
    private Integer templateId;

    /**
     * 短信模板参数
     */
    @ApiModelProperty("短信模板参数")
    private String params;

    /**
     * 短信发送时间
     */
    @ApiModelProperty("短信发送时间")
    private String createTime;

}
