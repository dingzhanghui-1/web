package com.learn.myweb.configurcation;

import java.time.Duration;

import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;


/**
 * 对@Caccheable 以分割符号 # 为过期时间间隔 进行设置过期时间,如果未设置,则以默认过期时间为准,
 */
public class MyRedisCacheManager extends RedisCacheManager {

    public MyRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration) {
        super(cacheWriter, defaultCacheConfiguration);
    }

    @Override
    protected RedisCache createRedisCache(String name, RedisCacheConfiguration cacheConfig) {
        if (name.contains("#")) {
            String[] array = name.split("#");
            name = array[0];
            long expirTime = Long.valueOf(array[1]);
            cacheConfig = cacheConfig.entryTtl(Duration.ofSeconds(expirTime));
        }
        return super.createRedisCache(name, cacheConfig);
    }
}
