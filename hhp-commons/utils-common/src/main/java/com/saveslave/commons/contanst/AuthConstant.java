package com.saveslave.commons.contanst;

import cn.hutool.core.util.StrUtil;

public final class AuthConstant {

    public static final String OAUTH_AUTHORIZATION = "Authorization";
    public static final String OAUTH_X_USER = "X-User";
    public static final String OAUTH_X_AGENT_ID = "X-Agent-id";
    public static final String OAUTH_X_USER_NAME = "X-User-Name";
    public static final String JWT_TOKEN_PREFIX = "Bearer ";
    public static final String JWT_USER_SOTYPE = "sotype";
    public static final String JWT_USER_NAME = "user_name";
    public static final String JWT_USER_TYPE = "user_type";
    public static final String JWT_OPEN_ID = "openid";
    public static final String JWT_UNION_ID = "unionid";
    public static final String REDIS_USER_PREFIX = "login_user:";
    public static final String REDIS_ACCOUNT_TOKEN_PREFIX = "login_account_token:";
    public static final String REDIS_LOGOUT_PREFIX = "login_out_token:";
    public static final String CLIENT_ID = "client_id";
    public static final String VALID_CLIENT_ID = "client-id";
    public static final String EXP_DATE = "exp";
    public static final String JWT_JTI = "jti";
    public static final String JWT_ATI = "ati";
    public static final String REFRESH_TOKEN_VAL = "Refresh_token";
    public static final String LOGOUT_TOKEN_VAL = "Logout_token";

    public static final Integer USER_TYPE_SYS = 3;

    /**
     * 浙品码注册用户类型
     */
    public static final Integer USER_TYPE_PIN = 4;

    public static final String SYS_USER_ADMIN = "admin";

    public static final String RESOURCE_ROLES_MAP_KEY = "RESOURCE_ROLES_MAP:";

    public static final String RESOURCE_URLS_KEY = "RESOURCE_URLS_KEY:";

    public static final String IS_FIRST_LOGIN = "isFirstLogin";

    public static final String PWD_IS_RESET = "pwdIsReset";

    public static final String PWD_EXPIRED = "pwdExpired";

    /**
     * 登出
     */
    public static final int LOGIN_OUT = 4000001;
    /**
     * 互剔，另一处登录
     */
    public static final int LOGIN_IN_RE = 4000002;
    /**
     * token 过期
     */
    public static final int TOKEN_EXPIRE = 4000003;
    /**
     * 刷新token 过期
     */
    public static final int REFRESH_TOKEN_EXPIRE = 4000004;

    public static String getUserOldTokenKey(String username) {
        return StrUtil.format("{}old_{}_token", AuthConstant.REDIS_ACCOUNT_TOKEN_PREFIX, username);
    }


}
