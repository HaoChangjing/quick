package com.saveslave.sysmanager.service.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
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
@ApiModel(value="SmsResultDTO对象", description="")
@ToString
public class SmsResultDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    private Long pId;

    @ApiModelProperty(value = "短信发送执行结果")
    private Double result;

    @ApiModelProperty(value = "短信发送执行结果信息")
    private String errMsg;

    @ApiModelProperty(value = "短信发送的扩展信息")
    private String ext;

    @ApiModelProperty(value = "接收手机号，群发短信中间以,隔开")
    private String phoneNum;

    @ApiModelProperty(value = "短信模板ID")
    private Double templateId;

    @ApiModelProperty(value = "短信模板参数")
    private String params;

    @ApiModelProperty(value = "短信发送时间")
    private String createTime;


}