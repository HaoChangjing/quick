package com.saveslave.commons.util;

import com.saveslave.commons.biz.model.MailDeployDTO;
import com.saveslave.commons.biz.model.SendParamDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Properties;

@Slf4j
@Component
public class SendMailUtil {

    public static boolean sendMail(SendParamDTO param, MailDeployDTO md) throws MessagingException, UnsupportedEncodingException {
        JavaMailSenderImpl  javaMailSender = new JavaMailSenderImpl();
        String[] split = md.getServerAddress().split(":");
        if(split.length==2){
            javaMailSender.setHost(split[0]);
            javaMailSender.setPort(StringUtils.isNotBlank(split[1])?Integer.parseInt(split[1].trim()):25);//默认使用25端口发送
        }else{

            javaMailSender.setHost(md.getServerAddress());
            javaMailSender.setPort(25);//默认使用25端口发送
        }

        javaMailSender.setUsername(md.getMailbox());//账号
        javaMailSender.setPassword(md.getMailboxPwd());//授权码
        javaMailSender.setDefaultEncoding("UTF-8");

        Properties properties = new Properties();
        //properties.setProperty("mail.debug", "true");//启用调试
        properties.setProperty("mail."+md.getTransportProtocol()+".timeout", "1000");//设置链接超时
        //设置通过ssl协议使用465端口发送、使用默认端口（25）时下面三行不需要
        if(md.getSecureConnection()!= BigDecimal.ZERO.intValue()){
            javaMailSender.setPort(465);
            properties.setProperty("mail."+md.getTransportProtocol()+".auth", "true");//开启认证
            properties.setProperty("mail.smtp.socketFactory.port", "465");//设置ssl端口
            properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        }
        javaMailSender.setJavaMailProperties(properties);
        MimeMessage message=javaMailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(message,true);
        helper.setFrom(md.getMailbox(),md.getDespatcher());
        helper.setTo(param.getConsigneeAddress());
        helper.setSubject(param.getTitle());
        helper.setText(param.getContent(),true);
        javaMailSender.send(message);
        return true;
    }

    public static void main(String[] args) throws MessagingException, UnsupportedEncodingException {
        SendParamDTO sendParamDTO = new SendParamDTO();
        sendParamDTO.setConsigneeAddress("876657800@qq.com");
        sendParamDTO.setOutsideCode("123456");
        sendParamDTO.setTitle("【hhp】用户忘记密码验证码");
        sendParamDTO.setContent("【hhp】用户忘记密码验证码" + "123456" + ",请在5分钟内进行输入,感谢使用");
        MailDeployDTO mailDeployDTO = new MailDeployDTO();
        mailDeployDTO.setServerAddress("smtp.qiye.163.com");
        mailDeployDTO.setMailbox("876657800@qq.com");
        mailDeployDTO.setMailboxPwd("1qaz2wsx..");
        mailDeployDTO.setTransportProtocol("smtp");
        mailDeployDTO.setDespatcher("hhp");
        mailDeployDTO.setSecureConnection(1);
        sendMail(sendParamDTO,mailDeployDTO);
    }
}
