package com.saveslave.commons.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.Map;
import java.util.TreeMap;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SignUtils {
    protected static final Logger logger = LoggerFactory.getLogger(SignUtils.class);
    private final static String CHARSET_UTF8 = "utf8";
    private final static String ALGORITHM = "UTF-8";
    private final static String SEPARATOR = "&";

    /**
     * 调用此方法签名
     * @param parameter 参与签名的字段
     * @param productSecret 平台提供的跟productKey对应的productSecrete
     * @return
     * @throws Exception
     */
    public static String generate(Map<String, String> parameter, String productSecret) {
        try {
            String signString = generateQueryString(parameter);
            byte[] signBytes = hmacSHA1Signature(productSecret + SEPARATOR, signString);
            String signature = newStringByBase64(signBytes);
            return signature;
        } catch (Exception e) {
            logger.error("签名生成异常", e);
            return "";
        }
    }

    /**
     * 对各字段按字典排序，拼接
     * @param params
     * @return
     */
    private static String generateQueryString(Map<String, String> params) throws UnsupportedEncodingException {
        TreeMap<String, String> sortParameter = new TreeMap<String, String>();
        sortParameter.putAll(params);
        StringBuilder canonicalizedQueryString = new StringBuilder();
        for (Map.Entry<String, String> entry : sortParameter.entrySet()) {
            if("sign".equals(entry.getKey())){
                continue;
            }
            canonicalizedQueryString.append(percentEncode(entry.getKey())).append("=")
                    .append(percentEncode(entry.getValue())).append(SEPARATOR);
        }
        if (canonicalizedQueryString.length() > 1) {
            canonicalizedQueryString.setLength(canonicalizedQueryString.length() - 1);
        }
        return canonicalizedQueryString.toString();
    }

    /**
     * 计算HMAC值
     * @param secret
     * @param baseString
     * @return
     * @throws Exception
     */
    private static byte[] hmacSHA1Signature(String secret, String baseString) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA1");
        SecretKeySpec keySpec = new SecretKeySpec(secret.getBytes(CHARSET_UTF8), ALGORITHM);
        mac.init(keySpec);
        return mac.doFinal(baseString.getBytes(CHARSET_UTF8));
    }

    /**
     * 计算签名值
     * @param bytes
     * @return
     * @throws UnsupportedEncodingException
     */
    private static String newStringByBase64(byte[] bytes) throws UnsupportedEncodingException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        return new String(Base64.getEncoder().encode(bytes), CHARSET_UTF8);
    }

    private static String percentEncode(String value) throws UnsupportedEncodingException {
        return value != null ? URLEncoder.encode(value, CHARSET_UTF8).replace("+", "%20")
                .replace("*", "%2A").replace("%7E", "~") : null;
    }

    /**
     * reverse a hex String representation
     * keeping the byte pairs together
     * @param in
     * @return
     */
    public static String reverseHex(String in){
        int len=in.length();
        StringBuilder sb=new StringBuilder(len);
        for (int i = 0; i < len/2; i++) {
            sb.append(in.charAt(len-(2*i)-2));
            sb.append(in.charAt(len-(2*i)-1));
        }
        return sb.toString();
    }

}
