package com.saveslave.commons.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.saveslave.commons.lock.DistributedLock;

/**
 * 
 * SuperService<br>
 * service接口父类
 *
 *
 * @param <DTO>
 */
public interface SuperService<DTO,DO> extends IService<DO> {
	/**
	 * 幂等性新增记录
	 *
	 * @param entity       实体对象
	 * @param lock         锁实例
	 * @param lockKey      锁的key
	 * @param queryEntity 判断是否存在的条件
	 * @param msg          对象已存在提示信息
	 * @return
	 */
	boolean saveIdempotency(DTO entity, DistributedLock lock, String lockKey, DTO queryEntity, String msg);

	/**
	 * 幂等性新增记录
	 *
	 * @param entity       实体对象
	 * @param lock         锁实例
	 * @param lockKey      锁的key
	 * @param countWrapper 判断是否存在的条件
	 * @return
	 */
	boolean saveIdempotency(DTO entity, DistributedLock lock, String lockKey, DTO countWrapper);

	/**
	 * 幂等性新增或更新记录
	 *
	 * @param entity       实体对象
	 * @param lock         锁实例
	 * @param lockKey      锁的key
	 * @param countWrapper 判断是否存在的条件
	 * @param msg          对象已存在提示信息
	 * @return
	 */
	boolean saveOrUpdateIdempotency(DTO entity, DistributedLock lock, String lockKey, DTO countWrapper,
			String msg);

	/**
	 * 幂等性新增或更新记录
	 *
	 * @param entity       实体对象
	 * @param lock         锁实例
	 * @param lockKey      锁的key
	 * @param countWrapper 判断是否存在的条件
	 * @return
	 */
	boolean saveOrUpdateIdempotency(DTO entity, DistributedLock lock, String lockKey, DTO countWrapper);
}
