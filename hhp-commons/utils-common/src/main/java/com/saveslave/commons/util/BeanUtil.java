package com.saveslave.commons.util;


import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BeanUtil {

    private static final Log log = LogFactory.get(BeanUtil.class);

    public static <S,T> T copyTo(S source, Class<T> clz) {
        try {
            if(source == null) {
                return null;
            }
            T clone = clz.newInstance();
            cn.hutool.core.bean.BeanUtil.copyProperties(source,clone);
            return clone;
        } catch (Exception e) {
            log.error("copy to single object error.",e);
            throw new RuntimeException(e);
        }
    }

    public static <S, T> List<T> copyListTo(List<S> sources, Class<T> clz) {
        try {
            if (sources == null) {
                return null;
            }
            List<T> newList = new ArrayList<>();
            if (sources.isEmpty()) {
                return newList;
            }
            for (S s : sources) {
                T clone = clz.newInstance();
                cn.hutool.core.bean.BeanUtil.copyProperties(s, clone);
                newList.add(clone);
            }
            return newList;
        } catch (Exception e) {
            log.error("copy to list object error.", e);
            throw new RuntimeException(e);
        }
    }

    public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public static void copyPropertiesIgnoreNull(Object src, Object target){
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }
}
