package com.saveslave.commons.properties;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 */
@Data
@Accessors(chain = true)
@Configuration
public class RedisProperties {

    @Value("${spring.redis.sentinel.nodes}")
    private String nodes;

    @Value("${spring.redis.sentinel.master}")
    private String masterSentinel;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.database}")
    private int database;

    @Value("${spring.redis.jedis.pool.max-active}")
    private int maxActive;

    @Value("${spring.redis.jedis.pool.max-wait}")
    private int maxWait;

    @Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.jedis.pool.min-idle}")
    private int minIdle;

    public List<String> getSentinelNodes() {
        nodes = StringUtils.defaultString(nodes, "");
        return Arrays.asList(StringUtils.split(nodes, ","));
    }

}
