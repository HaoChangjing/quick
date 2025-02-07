package com.saveslave.commons.contanst;

/**
 * 抽象枚举接口，提取公共方法
 *
 */
public interface IBaseEnum<K, V> {

    /**
     * 编码
     *
     * @return  K 编码
     */
    K getCode();

    /**
     * 名称
     *
     * @return  V 名称
     */
    V getName();

    /**
     * 获取枚举实例
     *
     * @param clazz 枚举class
     * @param code  代码
     * @return      枚举实例
     */
    static <E extends IBaseEnum<?, ?>> E get(Class<E> clazz, Object code) {
        for (E instance : clazz.getEnumConstants()) {
            if (instance.getCode().equals(code)) {
                return instance;
            }
        }
        return null;
    }

    /**
     * 获取枚举实例
     *
     * @param clazz         枚举class
     * @param code          编码
     * @param defaultCode   输入代码未获取到枚举实例时，根据默认编码返回实例
     * @return              枚举实例
     */
    static <E extends IBaseEnum<?, ?>> E get(Class<E> clazz, Object code, Object defaultCode) {
        E defaultInstance = null;
        for (E instance : clazz.getEnumConstants()) {
            if (instance.getCode().equals(code)) {
                return instance;
            }
            if (instance.getCode().equals(defaultCode)) {
                defaultInstance = instance;
            }
        }
        return defaultInstance;
    }

}

