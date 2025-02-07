package com.saveslave.commons.properties;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 */
@Data
@Accessors(chain = true)
@Component
public class RedissonProperties {

    /** 连接空闲超时，单位：毫秒 */
    @Value("${spring.redis.redisson.idleConnectionTimeout}")
    private int idleConnectionTimeout;

    /** 连接超时，单位：毫秒 */
    @Value("${spring.redis.redisson.connectTimeout}")
    private int connectTimeout;

    /** 等待超时，单位：毫秒 */
    @Value("${spring.redis.redisson.timeout}")
    private int timeout;

    /** 1.失败重试次数,如果尝试达到 retryAttempts（命令失败重试次数） 仍然不能将命令发送至某个指定的节点时，将抛出错误。
        2.如果尝试在此限制之内发送成功，则开始启用 timeout（命令等待超时） 计时。
     */
    @Value("${spring.redis.redisson.retryAttempts}")
    private int retryAttempts;

    /** 重试发送时间间隔，单位：毫秒 */
    @Value("${spring.redis.redisson.retryInterval}")
    private int retryInterval;

    /** 密码 */
    @Value("${spring.redis.redisson.password}")
    private String password;

    /** 单个连接最大订阅数量 */
    @Value("${spring.redis.redisson.subscriptionsPerConnection}")
    private int subscriptionsPerConnection;

    /** 节点地址 */
    @Value("${spring.redis.redisson.address}")
    private String address;

    /** 发布和订阅连接的最小空闲连接数 */
    @Value("${spring.redis.redisson.subscriptionConnectionMinimumIdleSize}")
    private int subscriptionConnectionMinimumIdleSize;

    /** 发布和订阅连接池大小 */
    @Value("${spring.redis.redisson.subscriptionConnectionPoolSize}")
    private int subscriptionConnectionPoolSize;

    /** 最小空闲连接数 */
    @Value("${spring.redis.redisson.connectionMinimumIdleSize}")
    private int connectionMinimumIdleSize;

    /** 连接池大小 */
    @Value("${spring.redis.redisson.connectionPoolSize}")
    private int connectionPoolSize;

    /** 数据库编号 */
    @Value("${spring.redis.redisson.database}")
    private int database;

    /** DNS监测时间间隔，单位：毫秒 */
    @Value("${spring.redis.redisson.dnsMonitoringInterval}")
    private int dnsMonitoringInterval;

    @Value("${spring.redis.sentinel.master}")
    private String masterSentinel;

    public List<String> getNodeAddresses() {
        address = StringUtils.defaultString(address, "");
        return Arrays.asList(StringUtils.split(address, ","));
    }

}
