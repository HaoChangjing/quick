package com.saveslave.sysmanager.service.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.saveslave.commons.service.impl.SuperServiceImpl;
import com.saveslave.commons.util.CurrentUserUtils;
import com.saveslave.sysmanager.sdk.model.dto.SmsSendDTO;
import com.saveslave.sysmanager.service.persistent.entity.SmsResultDO;
import com.saveslave.sysmanager.service.persistent.mapper.SmsResultMapper;
import com.saveslave.sysmanager.service.service.SmsResultService;
import com.saveslave.sysmanager.service.service.dto.SmsResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2019-10-12
 */
@Service
@Slf4j
public class SmsResultServiceImpl extends SuperServiceImpl<SmsResultMapper, SmsResultDO, SmsResultDTO> implements SmsResultService {
    private final static int APP_ID =1111111;
    private final static String APP_KEY = "you key";
    private final static String SMS_SIGN = "";

    @Override
    public boolean sendSms(SmsSendDTO smsSendDTO) {
        try {
            log.info("[SmsResultServiceImpl][sendSms]==>发送短信：phone:{} ,params:{}", smsSendDTO.getPhoneNumber(), JSONObject.toJSONString(smsSendDTO.getParams()));
            SmsSingleSender sender = new SmsSingleSender(APP_ID, APP_KEY);
            SmsSingleSenderResult result = sender.sendWithParam("86", smsSendDTO.getPhoneNumber(), smsSendDTO.getTemplateId(), smsSendDTO.getParams(), SMS_SIGN, "", "");

            log.info("[SmsResultServiceImpl][sendSms]==>发送短信：result:{}", JSONObject.toJSONString(result));

            SmsResultDO smsResultDO = new SmsResultDO()
                    .setResult(result.result)
                    .setErrMsg(result.errMsg)
                    .setBizId(smsSendDTO.getBizId())
                    .setExt(result.ext)
                    .setCreator(ObjectUtil.isNotEmpty(smsSendDTO.getAccount()) ? smsSendDTO.getAccount() : CurrentUserUtils.getLoginAccount())
                    .setParams(JSONObject.toJSONString(smsSendDTO.getParams()))
                    .setPhoneNum(smsSendDTO.getPhoneNumber())
                    .setTemplateId(smsSendDTO.getTemplateId())
                    .setCreateTime(DateUtil.format(DateTime.now(), DatePattern.NORM_DATETIME_PATTERN));
            Validator.validateTrue(this.save(smsResultDO), "新增短信记录失败");
            return true;
        } catch (Exception e) {
            log.error("[SmsResultServiceImpl][sendSms]==>exception: ", e);
            return false;
        }
    }

    public static void main(String[] args){
        String[] param={"logaaaa","BC1000001","2019年10月11日 18时07分"};

//        SmsUtil.sendSingleSms(SmsConstant.MARK_GENERATE_FAIL_TEMP,param,"18519188959");
    }
}
