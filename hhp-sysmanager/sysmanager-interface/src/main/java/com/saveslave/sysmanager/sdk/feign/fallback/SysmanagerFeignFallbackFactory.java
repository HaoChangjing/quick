package com.saveslave.sysmanager.sdk.feign.fallback;

import com.saveslave.sysmanager.sdk.feign.SysmanagerFeignClient;
import com.saveslave.sysmanager.sdk.model.dto.SmsSendDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SysmanagerFeignFallbackFactory implements FallbackFactory<SysmanagerFeignClient> {
    @Override
    public SysmanagerFeignClient create(Throwable throwable) {
        return new SysmanagerFeignClient() {

            public boolean sendSms(SmsSendDTO smsSendDTO) {
                log.error("BasicDataFeignClient.insertSmsResult 远程短信发送异常：" + smsSendDTO.toString(), throwable);
                return false;
            }


        };
    }

}
