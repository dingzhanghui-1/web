package com.learn.myweb.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class StringRedisService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    public String listLPop(String key) {
        return stringRedisTemplate.opsForList().leftPop(key);
    }


    public Object hashGet(String key, String hashKey) {
        return stringRedisTemplate.opsForHash().get(key, hashKey);
    }

    public boolean deleteKey(String key) {
        return stringRedisTemplate.delete(key);
    }

    public boolean judgeKey(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    public void clearKey(String key) {
        if (stringRedisTemplate.hasKey(key)) {
            stringRedisTemplate.opsForList().trim(key, 1, 0);
        }

    }

    /**
     * list  push 进入redis
     *
     * @param key
     * @param valueList
     */
    public void listRpushAll(String key, List<String> valueList) {
        stringRedisTemplate.opsForList().rightPushAll(key, valueList);
    }


    public List<String> listFindAll(String key) {
        if (!judgeKey(key)) {
            return new ArrayList<String>();
        }
        return stringRedisTemplate.opsForList().range(key, 0, -1);
    }

    public void listRpush(String key, String value) {
        stringRedisTemplate.opsForList().rightPush(key, value);
    }


    public Long incrmentRedisKey(String key) {
        RedisAtomicLong incrmentkey = new RedisAtomicLong(key, stringRedisTemplate.getConnectionFactory());
        Long incrment = incrmentkey.getAndIncrement();
        if (null != incrment && incrment.longValue() == 0) {
            incrmentkey.expire(2, TimeUnit.SECONDS);
        }
        return incrment;
    }


 


}
