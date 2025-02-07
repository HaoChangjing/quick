package com.saveslave.commons.util;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;

import java.util.*;

@Slf4j
public class SignUtil {

    private static final String BASE_STR="zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM1234567890";

    public static String getTimestamp() {
        //生成时间戳
        long timestampLong = System.currentTimeMillis();
        String timestampStr = String.valueOf(timestampLong);
        return timestampStr;
    }


    public  static String getNonceStr(int length) {
        String randomStr = RandomUtil.randomString(BASE_STR,length);
        return randomStr;
    }

    /**
     * @Date 2021/6/30 11:21
     * @Param [params, privateKey 私钥]
     * @Return java.lang.String
     * @Desc 签名生成方法
     *
     */
    public static String createSign(Map<String, Object> params, String privateKey,String timeStamp) {
        StringBuilder signSb = new StringBuilder();
        Map<String, Object> sortParams = new TreeMap<String, Object>(params);
        // 遍历排序的字典,并拼接"key=value"格式
        for (Map.Entry<String, Object> entry : sortParams.entrySet()) {
            String key = entry.getKey();
            String value = String.valueOf(entry.getValue()).trim();
            if (StringUtils.isNotBlank(value))
                signSb.append(key).append("=").append(value).append("&");
        }
        String stringSignTemp = signSb.append("appkey=").append(privateKey).toString();
        String[] signArr = stringSignTemp.split("&");
        Arrays.sort(signArr);  // 将参数排序
        stringSignTemp = arrToString(signArr);
        stringSignTemp.replaceAll("=","");
        log.info("stringSignTemp:{}",stringSignTemp);
        String signValue = DigestUtils.sha256Hex(stringSignTemp+timeStamp);
        log.info("signValue：  " + signValue);
        return signValue;
    }

    /**
     * 验证sign
     * @param params
     * @param privateKey
     * @param thridSign
     * @return
     */
    public static boolean verifySign(Map<String, Object> params, String privateKey, String thridSign, String timeStamp){
        String currentSign = createSign(params,privateKey,timeStamp);
        Validator.validateTrue(StringUtils.isNotBlank(thridSign),"sign签名不能为空");
        Validator.validateTrue(ObjectUtil.equal(thridSign,currentSign),"sign无效");
        Long supTime = Long.parseLong(getTimestamp()) - Long.parseLong(timeStamp);
        Validator.validateTrue((Long.parseLong(getTimestamp()) - Long.parseLong(timeStamp) <= (1000 * 60 * 5)),"sign已过期");
        return true;
    }

    private static String arrToString(String[] signArr){
        List<String> newArr = Arrays.asList(signArr);
        StringBuffer finalParam = new StringBuffer(Strings.EMPTY);
        newArr.stream().forEach(signParam ->{
            finalParam.append(signParam);
        });
        return finalParam.toString();
    }

}
