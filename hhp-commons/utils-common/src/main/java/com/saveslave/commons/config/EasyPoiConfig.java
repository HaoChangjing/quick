package com.saveslave.commons.config;

import cn.afterturn.easypoi.handler.impl.ExcelDataHandlerDefaultImpl;
import com.saveslave.commons.util.DtUtils;

import java.util.Date;

public class EasyPoiConfig<T> extends ExcelDataHandlerDefaultImpl<T> {
    @Override
    public Object exportHandler(T obj, String name, Object value) {
        if(value!=null){
            return DtUtils.format((Date)value, DtUtils.DATE_TIME_PATTERN);
        }
        return value;
    }
}