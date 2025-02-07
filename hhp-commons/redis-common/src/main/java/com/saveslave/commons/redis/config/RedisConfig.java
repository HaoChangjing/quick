package com.saveslave.commons.redis.config;

import com.saveslave.commons.properties.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.Resource;

/**
 * 哨兵模式Redis配置类
 *
 */
@Configuration
public class RedisConfig {

    @Resource
    private RedisProperties redisProperties;

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(redisProperties.getMaxActive());
        poolConfig.setMaxWaitMillis(redisProperties.getMaxWait());
        poolConfig.setMaxIdle(redisProperties.getMaxIdle());
        poolConfig.setMinIdle(redisProperties.getMinIdle());
        return poolConfig;
    }

    @Bean
    public RedisSentinelConfiguration redisSentinelConfiguration() {
        // 配置哨兵模式的主节点和哨兵节点
        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration()
                .master(redisProperties.getMasterSentinel());
        // 添加哨兵节点
        redisProperties.getSentinelNodes().forEach(node -> {
            String[] parts = node.split(":");
            sentinelConfig.sentinel(parts[0], Integer.parseInt(parts[1]));
        });
        sentinelConfig.setPassword(redisProperties.getPassword());
        sentinelConfig.setDatabase(redisProperties.getDatabase());
        return sentinelConfig;
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory(JedisPoolConfig poolConfig, RedisSentinelConfiguration sentinelConfig) {
        return new JedisConnectionFactory(sentinelConfig, poolConfig);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(JedisConnectionFactory jedisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        return template;
    }

}
