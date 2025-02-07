package com.saveslave.commons.redis.config;

import com.saveslave.commons.properties.RedissonProperties;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.annotation.Resource;

/**
 * 哨兵模式Redisson配置类
 *
 */
@Configuration
public class RedissonConfig {

    @Resource
    private RedissonProperties redissonProperties;

    /**
     * 集群装配
     */
    @Bean
    @Primary
    public RedissonClient getRedisson(){
        Config config = new Config();
        SentinelServersConfig sentinelServersConfig = config.useSentinelServers();
        sentinelServersConfig.setSentinelAddresses(redissonProperties.getNodeAddresses());
        sentinelServersConfig.setTimeout(redissonProperties.getTimeout());
        sentinelServersConfig.setDatabase(redissonProperties.getDatabase());
        sentinelServersConfig.setMasterName(redissonProperties.getMasterSentinel());
        sentinelServersConfig.setMasterConnectionMinimumIdleSize(redissonProperties.getConnectionMinimumIdleSize());
        sentinelServersConfig.setMasterConnectionPoolSize(redissonProperties.getConnectionPoolSize());
        sentinelServersConfig.setDnsMonitoringInterval(redissonProperties.getDnsMonitoringInterval());
        sentinelServersConfig.setSubscriptionConnectionMinimumIdleSize(redissonProperties.getSubscriptionConnectionMinimumIdleSize());
        sentinelServersConfig.setSubscriptionConnectionPoolSize(redissonProperties.getSubscriptionConnectionPoolSize());
        sentinelServersConfig.setConnectTimeout(redissonProperties.getConnectTimeout());
        sentinelServersConfig.setIdleConnectionTimeout(redissonProperties.getIdleConnectionTimeout());
        sentinelServersConfig.setPassword(redissonProperties.getPassword());
        sentinelServersConfig.setRetryAttempts(redissonProperties.getRetryAttempts());
        sentinelServersConfig.setRetryInterval(redissonProperties.getRetryInterval());
        sentinelServersConfig.setSubscriptionsPerConnection(redissonProperties.getSubscriptionsPerConnection());

        return Redisson.create(config);
    }
}
