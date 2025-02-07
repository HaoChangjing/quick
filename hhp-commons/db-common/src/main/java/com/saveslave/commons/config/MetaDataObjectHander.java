package com.saveslave.commons.config;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.saveslave.commons.contanst.DataState;
import com.saveslave.commons.util.CurrentUserUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * MetaDataObjectHander
 *
 */
@Slf4j
public class MetaDataObjectHander implements MetaObjectHandler {

    private final static String UPDATE_TIME = "updateTime";
    private final static String CREATE_TIME = "createTime";
    private final static String CREATOR_ID = "creatorId";
    private final static String UPDATER_ID = "updaterId";
    public static final String IS_DELETE = "isDelete";

    @Override
    public void insertFill(MetaObject metaObject) {
        Date currentTime = new Date();
        Long currentUserId = CurrentUserUtils.getUserIdLong();
        Field createTimeField = getFieldByName(metaObject, CREATE_TIME);
        if (ObjectUtil.isNotNull(createTimeField) && ObjectUtil.isNull(getFieldValByName(CREATE_TIME, metaObject))) {
            setFieldValByName(CREATE_TIME, currentTime, metaObject);
        }
        Field updateTimeField = getFieldByName(metaObject, UPDATE_TIME);
        if (ObjectUtil.isNotNull(updateTimeField) && ObjectUtil.isNull(getFieldValByName(UPDATE_TIME, metaObject))) {
            setFieldValByName(UPDATE_TIME, currentTime, metaObject);
        }
        Field creatorIdField = getFieldByName(metaObject, CREATOR_ID);
        if (ObjectUtil.isNotNull(creatorIdField) && ObjectUtil.isNull(getFieldValByName(CREATOR_ID, metaObject))) {
            setFieldValByName(CREATOR_ID, currentUserId, metaObject);
        }
        Field updaterIdField = getFieldByName(metaObject, UPDATER_ID);
        if (ObjectUtil.isNotNull(updaterIdField) && ObjectUtil.isNull(getFieldValByName(UPDATER_ID, metaObject))) {
            setFieldValByName(UPDATER_ID, currentUserId, metaObject);
        }
        Field isDeleteField = getFieldByName(metaObject, IS_DELETE);
        if (ObjectUtil.isNotNull(isDeleteField) && ObjectUtil.isNull(getFieldValByName(IS_DELETE, metaObject))) {
            setFieldValByName(IS_DELETE, DataState.UN_DELETED.getCode(), metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Date currentTime = new Date();
        Long currentUserId = CurrentUserUtils.getUserIdLong();
        Field updateTimeField = getFieldByName(metaObject, UPDATE_TIME);
        if (ObjectUtil.isNotNull(updateTimeField) && ObjectUtil.isNull(getFieldValByName(UPDATE_TIME, metaObject))) {
            setFieldValByName(UPDATE_TIME, currentTime, metaObject);
        }
        Field updaterIdField = getFieldByName(metaObject, UPDATER_ID);
        if (ObjectUtil.isNotNull(updaterIdField) && ObjectUtil.isNull(getFieldValByName(UPDATER_ID, metaObject)) && currentUserId != null) {
            setFieldValByName(UPDATER_ID, currentUserId, metaObject);
        }
    }

    private Field getFieldByName(MetaObject metaObject, String fileName) {
        Field field = ReflectUtil.getField(metaObject.getOriginalObject().getClass(), fileName);
        if (field == null) {
            if (metaObject.hasGetter(Constants.ENTITY)) {
                Object et = metaObject.getValue(Constants.ENTITY);
                if (et != null) {
                    field = ReflectUtil.getField(et.getClass(), fileName);
                }
            }
        }
        return field;
    }

}
