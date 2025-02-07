package com.saveslave.commons.template;

import org.redisson.api.*;
import org.redisson.config.Config;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName RedissonRepository
 **/
@Component
public class RedissonRepository {
    @Resource
    private RedissonClient redissonClient;

    public void getRedissonClient() throws IOException{
        Config config = redissonClient.getConfig();
        /*System.out.println(config.toJSON());*/
    }

    /**`
     * 获取字符串对象
     *
     * @param objectName
     * @return
     */
    public <T> RBucket<T> getRBucket(String objectName) {
        RBucket<T> bucket = redissonClient.getBucket(objectName);
        return bucket;
    }
    /**
     * 获取Map对象
     *
     * @param objectName
     * @return
     */
    public <K, V> RMap<K, V> getRMap(String objectName) {
        RMap<K, V> map = redissonClient.getMap(objectName);
        return map;
    }

    /**
     * 获取有序集合
     *
     * @param objectName
     * @return
     */
    public <V> RSortedSet<V> getRSortedSet(String objectName) {
        RSortedSet<V> sortedSet = redissonClient.getSortedSet(objectName);
        return sortedSet;
    }
    /**
     * 获取集合
     *
     * @param objectName
     * @return
     */
    public <V> RSet<V> getRSet(String objectName) {
        RSet<V> rSet = redissonClient.getSet(objectName);
        return rSet;
    }

    /**
     * 获取列表
     *
     * @param objectName
     * @return
     */
    public <V> RList<V> getRList(String objectName) {
        RList<V> rList = redissonClient.getList(objectName);
        return rList;
    }

    /**
     * 获取队列
     *
     * @param objectName
     * @return
     */
    public <V> RQueue<V> getRQueue(String objectName) {
        RQueue<V> rQueue = redissonClient.getQueue(objectName);
        return rQueue;
    }

    /**
     * 获取双端队列
     *
     * @param objectName
     * @return
     */
    public <V> RDeque<V> getRDeque(String objectName) {
        RDeque<V> rDeque = redissonClient.getDeque(objectName);
        return rDeque;
    }

    /**
     * 加锁
     *
     * @param objectName
     * @return
     */
    public RLock getRLock(String objectName) {
        RLock rLock = redissonClient.getLock(objectName);
        rLock.lock();
        return rLock;
    }

    /**
     * 加锁-超时时间
     *
     * @param objectName
     * @return
     */
    public RLock getRLock(String objectName,int leaseTime) {
        RLock rLock = redissonClient.getLock(objectName);
        rLock.lock(leaseTime, TimeUnit.SECONDS);
        return rLock;
    }

    /**
     * 加锁-自定义时区
     *
     * @param lockKey
     * @return
     */
    public RLock getRLock(String lockKey, TimeUnit unit ,int timeout) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(timeout, unit);
        return lock;
    }

    /**
     * 仅仅获取锁对象
     * @param lockKey
     * @return
     */
    public RLock onlyGetRLock(String lockKey) {
        return redissonClient.getLock(lockKey);
    }

    /**
     * 尝试加锁
     *
     * @param lockKey
     * @return
     */
    public boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            return false;
        }
    }

    /**
     * 尝试给 指定锁对象 加锁
     * @param lock - RLock lock
     * @param unit – time unit
     * @param waitTime – the maximum time to acquire the lock
     * @param leaseTime – lease time
     * @return lock result
     */
    public boolean tryLock(RLock lock, TimeUnit unit, int waitTime, int leaseTime) {
        try {
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            return false;
        }
    }

    /**
     * 解锁
     *
     * @param lockKey
     * @return
     */
    public void unlock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.unlock();
    }

    /**
     * 解锁：当前线程加锁成功，才解锁
     * @param lockKey
     */
    public void unlockByCurrentThread(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        unlockByCurrentThread(lock);
    }

    /**
     * 解锁：当前线程加锁成功，才解锁
     * @param lock
     */
    public void unlockByCurrentThread(RLock lock) {
        if(lock.isLocked() && lock.isHeldByCurrentThread()){
            lock.unlock();
        }
    }

    /**
     * 解锁
     *
     * @param lock
     * @return
     */
    public void unlock(RLock lock) {
        lock.unlock();
    }

    /**
     * 获取读取锁
     *
     * @param objectName
     * @return
     */
    public RReadWriteLock getRWLock(String objectName) {
        RReadWriteLock rwlock = redissonClient.getReadWriteLock(objectName);
        return rwlock;
    }

    /**
     * 获取原子数
     *
     * @param objectName
     * @return
     */
    public RAtomicLong getRAtomicLong(String objectName) {
        RAtomicLong rAtomicLong = redissonClient.getAtomicLong(objectName);
        return rAtomicLong;
    }

    /**
     * 获取记数锁
     *
     * @param objectName
     * @return
     */
    public RCountDownLatch getRCountDownLatch(String objectName) {
        RCountDownLatch rCountDownLatch = redissonClient.getCountDownLatch(objectName);
        return rCountDownLatch;
    }

    /**
     * 获取消息的Topic
     *
     * @param objectName
     * @return
     */
    /*public <M> RTopic<M> getRTopic(String objectName) {
        RTopic<M> rTopic = redissonClient.getTopic(objectName);
        return rTopic;
    }*/

}
