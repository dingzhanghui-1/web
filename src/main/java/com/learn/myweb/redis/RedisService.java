package com.learn.myweb.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RedisService<S, HK, V> {

    private RedisTemplate redisTemplate;

    private HashOperations<S, HK, V> hashOperations;
    private ListOperations<S, V> listOperations;
    private ZSetOperations<S, V> zSetOperations;
    private SetOperations<S, V> setOperations;
    private ValueOperations<S, V> valueOperations;


    @Autowired
    public RedisService(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
        this.listOperations = redisTemplate.opsForList();
        this.zSetOperations = redisTemplate.opsForZSet();
        this.setOperations = redisTemplate.opsForSet();
        this.valueOperations = redisTemplate.opsForValue();
    }


    /**
     * 往hash 里面放置
     * @param key
     * @param hashKey
     * @param value
     */
    public void hashPut(S key, HK hashKey, V value) {
        hashOperations.put(key, hashKey, value);
    }





    /**
     * 得到某个key 里面存储的键值对
     * @param key
     * @return
     */
    public Map<HK, V> hashFindAll(S key) {
        return hashOperations.entries(key);
    }

    /**
     * 得到 某个key里面存贮 hashKey 对应的value
     * @param key
     * @param hashKey
     * @return
     */
    public V hashGet(S key, HK hashKey) {
        return hashOperations.get(key, hashKey);
    }

    /**
     * 删除某个key 里面的hashKey 键值对
     * @param key
     * @param hashKey
     */
    public void hashRemove(S key, HK hashKey) {
        hashOperations.delete(key, hashKey);
    }

    /**
     * 得到某个key 存储的所有列表
     * @param key
     * @return
     */
    public List<V> listFindAll(S key) {
        if (!redisTemplate.hasKey(key)) {
            return new ArrayList<V>();
        }
        return listOperations.range(key, 0, -1);

    }

    /**
     * 左边弹出某个key列表对应的最左边的值
     * @param key
     * @return
     */
    public V listLPop(S key) {
        return listOperations.leftPop(key);
    }

    /**
     * 判断这个key 存不存在
     * @param key
     * @return
     */
    public boolean judgeKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 判断hash 里面hashKey 是否存在
     * @param key
     * @param hashKey
     * @return
     */
    public boolean judgeHashHasKey(S key, HK hashKey) {
        return hashOperations.hasKey(key, hashKey);
    }

    /**
     * 给某个自增的 初始化值
     * @param key
     * @param value
     */
    public void set(String key, int value) {
        RedisAtomicLong counter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        counter.set(value);
    }


    /**
     * 得到redis 中 某个可以字段的自增后的值
     * @param key
     * @return
     */
    public synchronized long generate(String key, int num) {
        if (!judgeKey(key)) {
            //int num = 100000;
            set(key, num);
        }
        RedisAtomicLong
                counter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        return counter.incrementAndGet();
    }


    /**
     * 清除key 的值
     * @param key
     */
    public void clearKey(S key) {
        if (redisTemplate.hasKey(key)) {
            listOperations.trim(key, 1, 0);
        }

    }

    /**
     * list  push 进入redis
     * @param key
     * @param valueList
     */
    public void listRpush(S key, List<V> valueList)
    {
        listOperations.rightPushAll(key, valueList);
    }


}
