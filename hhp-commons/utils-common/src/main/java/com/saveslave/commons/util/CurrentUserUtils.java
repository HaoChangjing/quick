package com.saveslave.commons.util;

import cn.hutool.core.codec.Base64;
import com.saveslave.commons.contanst.FieldConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

/**
 * 当前用户获取工具类
 *
 */
public class CurrentUserUtils {

    /**
     * 获取请求头中的指定字段
     */
    public static String get(String key) {
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            return request.getHeader(key);
        }
        return null;
    }

    /**
     * 获取userId头部信息
     */
    public static String getUserIdStr() {
        return get(FieldConstant.USER_ID);
    }

    public static Long getUserIdLong() {
        String userId = getUserIdStr();
        if (StringUtils.isNotBlank(userId)) {
            return Long.valueOf(userId);
        }
        return null;
    }

    public static String getUsername() {
        return Base64.decodeStr(get(FieldConstant.USERNAME), StandardCharsets.UTF_8);
    }

    public static String getLoginAccount() {
        return get(FieldConstant.LOGIN_ACCOUNT);
    }

    public static String getUserType() {
        return get(FieldConstant.USER_TYPE);
    }

    public static String getUserTypeCode() {
        return get(FieldConstant.USER_TYPE_CODE);
    }

    public static Long getOrgId() {
        String orgId = get(FieldConstant.ORG_ID);
        if (StringUtils.isNotBlank(orgId)) {
            return Long.valueOf(orgId);
        }
        return null;
    }

    public static String getOrgCode() {
        return get(FieldConstant.ORG_CODE);
    }

    public static String getOrgName() {
        return Base64.decodeStr(get(FieldConstant.ORG_NAME), StandardCharsets.UTF_8);
    }

    public static String getSupplierCode() {
        return get(FieldConstant.USER_TYPE_CODE);
    }

    public static String getUserTypeName() {
        return Base64.decodeStr(get(FieldConstant.USER_TYPE_NAME), StandardCharsets.UTF_8);
    }

    public static String getSoType() {
        return get(FieldConstant.SO_TYPE);
    }

}
