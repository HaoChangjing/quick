package com.saveslave.commons.redisson.locker.impl;

import com.saveslave.commons.redisson.locker.DistributedLocker;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedissonDistributedLocker implements DistributedLocker {

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public RLock lock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock();
        return lock;
    }

    @Override
    public RLock lock(String lockKey, int leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(leaseTime, TimeUnit.SECONDS);
        return lock;
    }

    @Override
    public RLock lock(String lockKey, TimeUnit unit , int timeout) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(timeout, unit);
        return lock;
    }

    @Override
    public boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            return false;
        }
    }

    @Override
    public void unlock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
     //   lock.unlock();
        if(lock.isLocked()){ // 是否还是锁定状态
            if(lock.isHeldByCurrentThread()){ // 时候是当前执行线程的锁
                lock.unlock(); // 释放锁
            }
        }
    }

    @Override
    public void unlock(RLock lock) {
        lock.unlock();
    }

//    public void setRedissonClient(RedissonClient redissonClient) {
//        this.redissonClient = redissonClient;
//    }

}
