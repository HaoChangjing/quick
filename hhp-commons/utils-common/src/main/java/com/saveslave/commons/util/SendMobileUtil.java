package com.saveslave.commons.util;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.saveslave.commons.biz.model.SmsDeployDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Component
public class SendMobileUtil {
    private static String regionId = "cn-hu";
    private static String sysDomain = "dysmsapi.aliyuncs.com";
    private static String sysVersion = "2017-05-25";
    private static String sysAction = "SendSms";

    /**
     * 发送短信获取验证码
     *
     * @param code
     * @param mobile
     */
    public static boolean sendLoginCode(String code, String mobile)
            throws UnsupportedEncodingException {
        // 必填：企业ID
        int userid = 11;
        // 必填：企业密码
        String md5 = "43543535";
        // 必填：短信内容的编码格式，值为8，表示utf-8
        int msgFmt = 8;
        // 必填：短信内容： 1、短信内容由签名和内容两部分组成，签名格式为：【xxx】,其中xxx一般为公司名称；示例：【云掌通】验证码123456 2、需要对短信内容进行URLEncode。
        String message = "【hhp】用户忘记密码验证码" + code + "，请在5分钟内进行输入，感谢使用";
        String charset = "utf-8";
        // 非必填：扩展号，可为空字符串
        String ext = "";
        String param = String.format(
                "userid=%s&passwordMd5=%s&msg_fmt=%s&message=%s&mobile=%s&ext=", userid, md5,
                msgFmt,
                URLEncoder.encode(message, charset), mobile, ext);
        // 调用接口，注意：请求内容类型content-type为application/x-www-form-urlencoded
        // 接口返回值是一个整数，大于0表示提交成功，小于0表示提交错误
        String result = HttpUtil.post("http://1.0.0.1:8085/sendsms.php", param);
        log.info("[SendMobileUtil][sendLoginCode]result:{}", result);

        return Long.parseLong(result) > 0;
    }

    /**
     * 使用阿里云平台发送短信验证码
     *
     * @param code
     * @param mobile
     * @param smsDeployDTO
     * @return
     */
    public static boolean sendAliyunSmsCode(String code, String mobile, SmsDeployDTO smsDeployDTO) {


        /**
         * 连接阿里云：
         *
         * 三个参数：
         * regionId 不要动，默认使用官方的
         * accessKeyId 自己的用户accessKeyId
         * accessSecret 自己的用户accessSecret
         */
        DefaultProfile profile = DefaultProfile.getProfile(
                regionId, smsDeployDTO.getAccessKeyId(), smsDeployDTO.getAccessKeySecret());
        IAcsClient client = new DefaultAcsClient(profile);
        log.info("获取发送短信客户端 client:{}", client);
        // 构建请求：
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain(sysDomain);
        request.setSysVersion(sysVersion);
        request.setSysAction(sysAction);

        // 自定义参数：
        request.putQueryParameter("PhoneNumbers", mobile);// 接收短信的手机号
        request.putQueryParameter("SignName", smsDeployDTO.getSmsSign());// 短信签名
        request.putQueryParameter("TemplateCode", smsDeployDTO.getTemplateCode());// 短信模版CODE

        // 构建短信验证码
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(map));

        try {
            CommonResponse response = client.getCommonResponse(request);
            log.info("获取发送短信响应结果 date：{}",response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        sendLoginCode("123456", "3333333");
    }
}
