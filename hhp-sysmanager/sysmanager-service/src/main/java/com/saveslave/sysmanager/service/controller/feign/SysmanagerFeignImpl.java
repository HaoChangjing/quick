package com.saveslave.sysmanager.service.controller.feign;

import com.saveslave.sysmanager.sdk.feign.SysmanagerFeignClient;
import com.saveslave.sysmanager.sdk.model.dto.SmsSendDTO;
import com.saveslave.sysmanager.service.service.SmsResultService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class SysmanagerFeignImpl implements SysmanagerFeignClient {


    @Resource
    private SmsResultService smsResultService;


    @Override
    public boolean sendSms(SmsSendDTO smsSendDTO) {
        return smsResultService.sendSms(smsSendDTO);
    }


}
