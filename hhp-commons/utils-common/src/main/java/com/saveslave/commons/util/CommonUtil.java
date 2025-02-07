package com.saveslave.commons.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class CommonUtil {

    /**
     * 比较对象
     * @param t1
     * @param t2
     * @param <T>
     * @return
     */
    public static <T> boolean equals(T t1, T t2) {
        return t1 == null? (t2 == null) : t1.equals(t2);
    }

    /**
     * 比较对象
     * @param t1
     * @param t2
     * @param <T>
     * @return
     */
    public static <T> boolean notEquals(T t1, T t2) {
        return !equals(t1, t2);
    }

    /**
     * 判断对象是否在指定几个对象中
     * @param t1
     * @param ts
     * @param <T>
     * @return
     */
    public static <T> boolean in(T t1, T... ts) {
        if(t1 != null) {
            for(T t : ts) {
                if(equals(t1, t)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断对象是否不在指定几个对象中
     * @param t1
     * @param ts
     * @param <T>
     * @return
     */
    public static <T> boolean notIn(T t1, T... ts) {
        return !in(t1, ts);
    }

    /**
     * 格式化数值字符串
     * @param number
     * @return
     */
    public static String formatStringNumber(String number, String defaultNumber) {
        if(number == null || number.length() == 0) {
            return defaultNumber;
        }

        try {
            return new BigDecimal(number.trim()).stripTrailingZeros().toPlainString();
        } catch (Exception e) {
            return defaultNumber;
        }
    }

    /**
     * number >= 0 返回true
     * @param number
     * @return
     */
    public static boolean compareNotLessThanZero(String number) {
        if(number == null || number.length() == 0) {
            return false;
        }

        try {
            return new BigDecimal(number.trim()).compareTo(BigDecimal.ZERO) >= 0;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * n1 x n2
     * @param n1
     * @param n2
     * @param defaultNumber
     * @return
     */
    public static String mulStringNumber(String n1, String n2, String defaultNumber) {
        if(n1 == null || n1.length() == 0 || n2 == null || n2.length() == 0) {
            return defaultNumber;
        }

        try {
            return new BigDecimal(n1.trim()).multiply(new BigDecimal(n2.trim())).stripTrailingZeros().toPlainString();
        } catch (Exception e) {
            return defaultNumber;
        }
    }

    /**
     * n1 x n2
     * @param n1
     * @param n2
     * @param defaultNumber
     * @return
     */
    public static String mulStringLongNumber(String n1, Long n2, String defaultNumber) {
        return mulStringNumber(n1, n2 != null?n2.toString() : null, defaultNumber);
    }

    /**
     * 转换数值字符串到BigDecimal
     * @param number
     * @param defaultNumber
     * @return
     */
    public static BigDecimal convertStringNumber(String number, BigDecimal defaultNumber) {
        if(number == null || number.length() == 0) {
            return defaultNumber;
        }

        try {
            return new BigDecimal(number.trim());
        } catch (Exception e) {
            return defaultNumber;
        }
    }

    /**
     * 返回集合大小, null 或 空返回0
     * @param collection
     * @return
     */
    public static int size(Collection<?> collection) {
        return collection != null?collection.size() : 0;
    }

    /**
     * 去除前导0
     * @param str
     * @return
     */
    public static String removePreZero(String str) {
        if(StringUtils.isBlank(str)) {
            return str;
        }
        return str.replaceFirst("^0*", "");
    }

    /**
     * 如果t为null,返回defaultT
     * @param t
     * @param defaultT
     * @param <T>
     * @return
     */
    public static <T> T defaultIfNull(T t, T defaultT) {
        if(t == null) {
            return defaultT;
        }

        return t;
    }

    /**
     * 将t2加入到t1中，t2允许为null
     * @param t1
     * @param t2
     * @param <T>
     */
    public static <T> void addCollection(Collection<T> t1, Collection<T> t2) {
        if(t1 != null && t2 != null && t2.size() > 0) {
            t1.addAll(t2);
        }
    }

    /**
     * 判断日期是否符合指定格式
     * @param dateStr 日期字符串
     * @param format  日期格式
     * @return true: 符合 false: 不符合
     */
    public static boolean isLegalDate(String dateStr, String format) {
        if (StringUtils.isBlank(dateStr) || StringUtils.isBlank(format)) {
            return false;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
            Date date = formatter.parse(dateStr);
            return dateStr.equals(formatter.format(date));
        } catch (Exception e) {
            return false;
        }

    }
}
