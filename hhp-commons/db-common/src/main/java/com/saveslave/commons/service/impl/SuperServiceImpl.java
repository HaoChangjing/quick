package com.saveslave.commons.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.saveslave.commons.exception.IdempotentException;
import com.saveslave.commons.exception.LockException;
import com.saveslave.commons.lock.DistributedLock;
import com.saveslave.commons.service.SuperService;
import com.saveslave.commons.util.BeanUtil;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

/**
 * 
 * SuperServiceImpl
 * service实现父类
 *
 * @param <M>
 * @param <DO>
 */
public class SuperServiceImpl<M extends BaseMapper<DO>, DO, DTO> extends ServiceImpl<M, DO> implements SuperService<DTO,DO> {
    Class<DO> doClass;
    Class<DTO> dtoClass;

    public SuperServiceImpl() {
        //得到父类的泛型
        Type sType=getClass().getGenericSuperclass();
        //得到实际的类型参数数组
        Type[] generics=((ParameterizedType) sType).getActualTypeArguments();
        //得到第一个泛型的Class
        doClass = (Class<DO>) (generics[1]);
        dtoClass = (Class<DTO>) (generics[2]);
    }
    /**
     * 幂等性新增记录
     * 例子如下：
     * String username = sysUser.getUsername();
     * boolean result = super.saveIdempotency(sysUser, lock
     *                 , LOCK_KEY_USERNAME+username
     *                 , new QueryWrapper<SysUser>().eq("username", username));
     *
     * @param entity       实体对象
     * @param lock         锁实例
     * @param lockKey      锁的key
     * @param queryEntity 判断是否存在的条件
     * @param msg          对象已存在提示信息
     * @return
     */
    @Override
    public boolean saveIdempotency(DTO entity, DistributedLock lock, String lockKey, DTO queryEntity, String msg) {
        if (lock == null) {
            throw new LockException("DistributedLock is null");
        }
        if (StrUtil.isEmpty(lockKey)) {
            throw new LockException("lockKey is null");
        }
        try {
            //加锁
            boolean isLock = lock.lock(lockKey);
            if (isLock) {
                //判断记录是否已存在
                DO queryDTO= BeanUtil.copyTo(queryEntity,this.doClass);
                Wrapper<DO> countWrapper=new QueryWrapper(queryDTO);
                int count = super.count(countWrapper);
                if (count == 0) {
                    return super.save(this.convertFromDTO(entity));
                } else {
                    if (StrUtil.isEmpty(msg)) {
                        msg = "已存在";
                    }
                    throw new IdempotentException(msg);
                }
            } else {
                throw new LockException("锁等待超时");
            }
        } finally {
            lock.releaseLock(lockKey);
        }
    }

    /**
     * 幂等性新增记录
     *
     * @param entity       实体对象
     * @param lock         锁实例
     * @param lockKey      锁的key
     * @param countWrapper 判断是否存在的条件
     * @return
     */
    @Override
    public boolean saveIdempotency(DTO entity, DistributedLock lock, String lockKey, DTO countWrapper) {
        return saveIdempotency(entity, lock, lockKey, countWrapper, null);
    }

    /**
     * 幂等性新增或更新记录
     * 例子如下：
     * String username = sysUser.getUsername();
     * boolean result = super.saveOrUpdateIdempotency(sysUser, lock
     *                 , LOCK_KEY_USERNAME+username
     *                 , new QueryWrapper<SysUser>().eq("username", username));
     *
     * @param entity       实体对象
     * @param lock         锁实例
     * @param lockKey      锁的key
     * @param countWrapper 判断是否存在的条件
     * @param msg          对象已存在提示信息
     * @return
     */
    @Override
    public boolean saveOrUpdateIdempotency(DTO entity, DistributedLock lock, String lockKey, DTO countWrapper, String msg) {
        if (null != entity) {
            Class<?> cls = entity.getClass();
            TableInfo tableInfo = TableInfoHelper.getTableInfo(cls);
            if (null != tableInfo && StringUtils.isNotBlank(tableInfo.getKeyProperty())) {
                Object idVal = ReflectionKit.getFieldValue(entity, tableInfo.getKeyProperty());
                if (StringUtils.checkValNull(idVal) || Objects.isNull(getById((Serializable) idVal))) {
                    if (StrUtil.isEmpty(msg)) {
                        msg = "已存在";
                    }
                    return this.saveIdempotency(entity, lock, lockKey, countWrapper, msg);
                } else {
                    return updateById(this.convertFromDTO(entity));
                }
            } else {
                throw ExceptionUtils.mpe("Error:  Can not execute. Could not find @TableId.");
            }
        }
        return false;
    }

    /**
     * 幂等性新增或更新记录
     * 例子如下：
     * String username = sysUser.getUsername();
     * boolean result = super.saveOrUpdateIdempotency(sysUser, lock
     *                 , LOCK_KEY_USERNAME+username
     *                 , new QueryWrapper<SysUser>().eq("username", username));
     *
     * @param entity       实体对象
     * @param lock         锁实例
     * @param lockKey      锁的key
     * @param countWrapper 判断是否存在的条件
     * @return
     */
    @Override
    public boolean saveOrUpdateIdempotency(DTO entity, DistributedLock lock, String lockKey, DTO countWrapper) {
        return this.saveOrUpdateIdempotency(entity, lock, lockKey, countWrapper, null);
    }

    protected List<DTO> convertFromDOs(List<DO> doObjects) {
        return BeanUtil.copyListTo(doObjects, this.dtoClass);
    }
    protected DTO convertFromDO(DO doObject) {
        return BeanUtil.copyTo(doObject, this.dtoClass);
    }
    protected DO convertFromDTO(DTO dtoObject) {
        return BeanUtil.copyTo(dtoObject, this.doClass);
    }

    protected List<DO> convertFromDTOs(List<DTO> dtoObjects) {
        return BeanUtil.copyListTo(dtoObjects, this.doClass);
    }
    
    protected IPage<DTO> convertFromDOPages(IPage<DO> doPages) {
        List<DTO> dtoList = BeanUtil.copyListTo(doPages.getRecords(), this.dtoClass);
        IPage<DTO> dtoListPage = new Page<>();
        dtoListPage.setCurrent(doPages.getCurrent());
        dtoListPage.setSize(doPages.getSize());
        dtoListPage.setTotal(doPages.getTotal());
        dtoListPage.setRecords(dtoList);
        return dtoListPage;
    }

}
