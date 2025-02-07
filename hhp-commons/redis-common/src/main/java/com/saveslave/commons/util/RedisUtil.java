package com.saveslave.commons.util;

import cn.hutool.core.date.DatePattern;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;
import redis.clients.jedis.exceptions.JedisDataException;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 *
 **/
@Component
public class RedisUtil {

    private static StringRedisTemplate redisTemplate;
    private static RedisTemplate<String, Object> redisTemplates;

    /**
     * 默认编码
     */
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    @Autowired
    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    @Autowired
    public void setRedisTemplates(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplates = redisTemplate;
    }
    /**
     * 判断某个主键是否存在
     *
     * @param key the key
     * @return the boolean
     */
    public static boolean exists(final String key) {
        return redisTemplate
                .execute((RedisCallback<Boolean>) connection -> connection.exists(key.getBytes(DEFAULT_CHARSET)));
    }

    public  static  boolean setMap(String key, Object map,
                                   final long time){

        redisTemplates.opsForValue().set(key,map,time, TimeUnit.SECONDS);
        return true;
    }
    /**
     * 添加到带有 过期时间的 缓存
     *
     * @param key   redis主键
     * @param value 值
     * @param time  过期时间(单位秒)
     */
    public static boolean set(final String key, final String value,
                              final long time) {
        boolean b = false;
        try {
            b = (redisTemplate.execute(new RedisCallback<Boolean>() {
                public Boolean doInRedis(RedisConnection con)
                        throws DataAccessException {
                    try {
                        byte[] keys = redisTemplate.getStringSerializer().serialize(key);
                        con.set(keys, redisTemplate.getStringSerializer().serialize(value));
                        con.expire(keys, time);
                    } catch (JedisDataException e) {
                        return false;
                    }
                    return true;
                }
            })).booleanValue();
        } catch (Exception e) {
            e.printStackTrace();
            b = false;
        }
        return true;
    }

    /**
     *  计数器
     */
    public static Long incr(String key) {
        RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        return entityIdCounter.getAndIncrement();
    }

    public static String generateSerialNumberByDay(String keyPrefix) {
        String today = DateFormatUtils.format(new Date(), DatePattern.PURE_DATE_PATTERN);
        String key = StringUtils.defaultString(keyPrefix, "") + today;
        long serial = incr(key);
        redisTemplate.expire(key, 1, TimeUnit.DAYS);
        return String.format("%s%06d", today, serial);
    }

}
