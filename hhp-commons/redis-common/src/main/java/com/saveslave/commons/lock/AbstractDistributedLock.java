package com.saveslave.commons.lock;

/**
 * 
    * @ClassName: AbstractDistributedLock
    * @Description: 分布式锁抽象类
    *
 */
public abstract class AbstractDistributedLock implements DistributedLock{

    @Override
    public boolean lock(String key) {
        return lock(key, TIMEOUT_MILLIS, RETRY_TIMES, SLEEP_MILLIS);
    }

    @Override
    public boolean lock(String key, int retryTimes) {
        return lock(key, TIMEOUT_MILLIS, retryTimes, SLEEP_MILLIS);
    }

    @Override
    public boolean lock(String key, int retryTimes, long sleepMillis) {
        return lock(key, TIMEOUT_MILLIS, retryTimes, sleepMillis);
    }

    @Override
    public boolean lock(String key, long expire) {
        return lock(key, expire, RETRY_TIMES, SLEEP_MILLIS);
    }

    @Override
    public boolean lock(String key, long expire, int retryTimes) {
        return lock(key, expire, retryTimes, SLEEP_MILLIS);
    }
}
