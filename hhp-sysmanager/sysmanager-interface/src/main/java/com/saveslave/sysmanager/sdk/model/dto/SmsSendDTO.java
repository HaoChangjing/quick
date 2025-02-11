package com.saveslave.sysmanager.sdk.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@ApiModel("短信发送DTO")
@Accessors(chain = true)
public class SmsSendDTO {
    /**
     * com.panpass.commons.contanst.SmsConstant
     */
    @ApiModelProperty("短信模版id")
    private Integer templateId;

    @ApiModelProperty("短信内容")
    private String[] params;

    @ApiModelProperty("电话号码")
    private String phoneNumber;

    @ApiModelProperty("业务id")
    private Long bizId;

    private String account;
}
