package com.saveslave.sysmanager.sdk.feign;

import com.saveslave.sysmanager.sdk.feign.fallback.SysmanagerFeignFallbackFactory;
import com.saveslave.sysmanager.sdk.model.dto.SmsSendDTO;
import com.saveslave.commons.contanst.ServiceNameConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = ServiceNameConstants.SYSMANAGER_SERVICE, fallbackFactory = SysmanagerFeignFallbackFactory.class, decode404 = true)
public interface SysmanagerFeignClient {

    @PostMapping(value = "/sysmanager-feign/sendSms")
    boolean sendSms(@RequestBody SmsSendDTO smsSendDTO);

}
