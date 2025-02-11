package com.saveslave.sysmanager.service.service;

import com.saveslave.commons.service.SuperService;
import com.saveslave.sysmanager.sdk.model.dto.SmsSendDTO;
import com.saveslave.sysmanager.service.persistent.entity.SmsResultDO;
import com.saveslave.sysmanager.service.service.dto.SmsResultDTO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2019-10-12
 */
public interface SmsResultService extends SuperService<SmsResultDTO, SmsResultDO> {
    boolean sendSms(SmsSendDTO smsSendDTO);
}
