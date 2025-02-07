package com.saveslave.commons.redis.config;

import com.saveslave.commons.template.FastJsonRedisRepository;
import com.saveslave.commons.template.RedisRepository;
import com.saveslave.commons.template.StringRedisRepository;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;
import java.time.Duration;

/**
 * 
 * @ClassName: RedisCacheConfig
 * @Description: redis 配置类
 *
 */
@Configuration
@ConditionalOnClass(RedisRepository.class)
@AutoConfigureAfter(RedisAutoConfiguration.class)
@EnableCaching
public class RedisCacheConfig extends CachingConfigurerSupport {
	/** redis 缓存默认过期时间单位：秒 */
	private static final long DEFAULT_EXPIRATION_TIME = 600L;

	@Bean
	@ConditionalOnMissingBean(name = "redisTemplate")
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		RedisSerializer stringSerializer = new StringRedisSerializer();
		RedisSerializer redisObjectSerializer = new RedisObjectSerializer();
		/*GenericFastJsonRedisSerializer genericFastJsonRedisSerializer = new GenericFastJsonRedisSerializer();*/
		redisTemplate.setKeySerializer(stringSerializer);
		redisTemplate.setHashKeySerializer(stringSerializer);
		redisTemplate.setValueSerializer(redisObjectSerializer);
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}

    @Bean
	@ConditionalOnMissingBean
    public RedisRepository redisRepository(RedisTemplate<String, Object> redisTemplate) {
        return new RedisRepository(redisTemplate);
    }

	@Bean
	@ConditionalOnMissingBean(FastJsonRedisRepository.class)
	public FastJsonRedisRepository fastJsonRedisRepository(RedisConnectionFactory redisConnectionFactory) {
		FastJsonRedisRepository fastJsonRedisRepository = new FastJsonRedisRepository();
		fastJsonRedisRepository.setConnectionFactory(redisConnectionFactory);
		return fastJsonRedisRepository;
	}

	@Bean
	@ConditionalOnMissingBean(StringRedisRepository.class)
	public StringRedisRepository stringRedisRepository(RedisConnectionFactory redisConnectionFactory) {
		StringRedisRepository stringRedisRepository = new StringRedisRepository();
		stringRedisRepository.setConnectionFactory(redisConnectionFactory);
		return stringRedisRepository;
	}

	@Bean
	@Primary
	public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
		RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.
				defaultCacheConfig().entryTtl(Duration.ofSeconds(DEFAULT_EXPIRATION_TIME));
		return RedisCacheManager
				.builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
				.cacheDefaults(redisCacheConfiguration).build();
	}

	@Bean
	@Override
	public KeyGenerator keyGenerator() {
		return (target, method, objects) -> {
			StringBuilder sb = new StringBuilder();
			sb.append(target.getClass().getName());
			sb.append(":" + method.getName() + ":");
			for (Object obj : objects) {
				sb.append(obj.toString());
			}
			return sb.toString();
		};
	}
}
