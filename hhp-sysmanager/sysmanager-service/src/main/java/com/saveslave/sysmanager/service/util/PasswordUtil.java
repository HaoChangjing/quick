package com.saveslave.sysmanager.service.util;

import cn.hutool.crypto.digest.BCrypt;

/**
 * @description:
 * @author: Haocj
 * @time: 2025/2/12
 */
public class PasswordUtil {
    /**
     * 加密密码
     *
     * @param plainTextPassword 明文密码
     * @return 加密后的密码哈希值
     */
    public static String encryptPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    /**
     * 验证密码
     *
     * @param plainTextPassword 明文密码
     * @param hashedPassword   储存在数据库中的密码哈希值
     * @return 如果密码匹配，返回true，否则返回false
     */
    public static boolean verifyPassword(String plainTextPassword, String hashedPassword) 	  {
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }
}
