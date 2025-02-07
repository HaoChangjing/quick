package com.saveslave.commons.util;

import java.util.Locale;

/**
 */
public class ExceptionUtil {

    /**
     * 递归异常链获取最早抛出的异常
     *
     * @param throwable
     * @return
     */
    public static Throwable getOriginalCause(Throwable throwable) {
        if (throwable == null || throwable.getCause() == null) {
            return throwable;
        }
        return getOriginalCause(throwable.getCause());
    }

    /**
     * 获取异常信息
     *
     * @param throwable
     * @return
     */
    public static String getOriginalMessage(Throwable throwable) {
        if (throwable == null) {
            return "";
        }
        Throwable original = getOriginalCause(throwable);
        if (original == null) {
            return "";
        }
        return messageSimplified(original.getMessage());
    }

    public static String getOriginalMessageFull(Throwable throwable) {
        Throwable original = getOriginalCause(throwable);
        if (original == null) {
            return "";
        }
        return original.getMessage();
    }

    /**
     * 去掉sql相关内容
     *
     * @param message
     * @return
     */
    public static String messageSimplified(String message) {
        if (message == null || message.length() == 0) {
            return message;
        }
        int endIndex = message.length();
        String msgLower = message.toLowerCase(Locale.ROOT);
        if (msgLower.contains("select ") && msgLower.indexOf("select ") < endIndex) {
            endIndex = msgLower.indexOf("select ");
        }
        if (msgLower.contains("insert ") && msgLower.indexOf("insert ") < endIndex) {
            endIndex = msgLower.indexOf("insert");
        }
        if (msgLower.contains("update ") && msgLower.indexOf("update ") < endIndex) {
            endIndex = msgLower.indexOf("update ");
        }
        if (msgLower.contains("delete ") && msgLower.indexOf("delete ") < endIndex) {
            endIndex = msgLower.indexOf("delete ");
        }
        if (msgLower.contains("merge ") && msgLower.indexOf("merge ") < endIndex) {
            endIndex = msgLower.indexOf("merge ");
        }
        return message.substring(0, endIndex);
    }
}
