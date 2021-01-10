package com.sdm.test.service;

import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * All rights Reserved, Designed By dongming.shang
 *
 * @version V1.0
 * @Title:
 * @Package com.sdm.test.service
 * @Description：
 * @author: sdm
 * @date: 2021/1/10 16:16
 * @Copyright: 2021 R} dongming.shang All rights reserved.
 */
@Component
public class RedisManager {

    @Autowired
    private RedisTemplate redisTemplate ;

    private RedissonClient redisClient ;



    /**
     * 更新过期时间
     * @param key
     * @param expire
     * @param timeUnit
     */
    public void updateTTL(Object key ,long expire , TimeUnit timeUnit){
        redisTemplate.expire(key,expire,timeUnit);
    }


    /**
     * 存储为k-v
     *
     * @param key
     * @param value
     * @param <T>
     */
    public <T> void save(Object key, T value) {
        redisTemplate.boundValueOps(key).set(value);
    }

    /**
     * 存储为k-v
     *
     * @param key
     * @param value
     * @param <T>
     */
    public <T> void save(Object key, T value, Long expire, TimeUnit timeUnit) {
        redisTemplate.boundValueOps(key).set(value, expire, timeUnit);
    }


    /**
     * 通过k 获取v
     *
     * @param key
     * @param <T>
     * @return
     */
    public <T> T getValue(Object key) {
        BoundValueOperations<String, T> operations = redisTemplate.boundValueOps(key);
        return operations.get();
    }

    public <T> List<T> getValueList(Object key) {
        BoundValueOperations<String, List<T>> operations = redisTemplate.boundValueOps(key);
        return operations.get();
    }


    public <T> void leftPush(Object key, T value) {
        BoundListOperations<String, T> operations = redisTemplate.boundListOps(key);
        operations.leftPush(value);
    }

    public <T> T leftPop(Object key) {
        BoundListOperations<String, T> operations = redisTemplate.boundListOps(key);
        return operations.leftPop();
    }

    public <T> void rightPush(Object key, T value) {
        BoundListOperations<String, T> operations = redisTemplate.boundListOps(key);
        operations.rightPush(value);
    }

    public <T> T rightPop(Object key) {
        BoundListOperations<String, T> operations = redisTemplate.boundListOps(key);
        return operations.rightPop();
    }

    /**
     * @param hkey  表名
     * @param key   键名
     * @param value 值
     * @param <T>
     */
    public <T> void hashPut(Object hkey, String key, T value) {
        BoundHashOperations<String, String, T> operations = redisTemplate.boundHashOps(hkey);
        operations.put(key, value);
    }

    public <T> List<T> multiGet(String hkey, Collection<String> keys) {
        BoundHashOperations<String, String, T> operations = redisTemplate.boundHashOps(hkey);
        List<T> list = operations.multiGet(keys);
        return list;

    }


    /**
     * @param hkey 表名
     * @param key  键名
     * @param <T>
     */
    public <T> T hashGet(String hkey, String key) {
        BoundHashOperations<String, String, T> operations = redisTemplate.boundHashOps(hkey);
        return operations.get(key);
    }

    /**
     * @param hkey 表名
     * @param <T>
     */
    public <T> Map<String, T> hashGetAll(String hkey) {
        BoundHashOperations<String, String, T> operations = redisTemplate.boundHashOps(hkey);
        return operations.entries();
    }

    public <T> T setPop(String key) {
        BoundSetOperations<String, T> operations = redisTemplate.boundSetOps(key);
        return operations.pop();
    }

    public <T> void setAdd(String key, T value) {
        BoundSetOperations<String, T> operations = redisTemplate.boundSetOps(key);
        operations.add(value);
    }

    public <T> void zsetAdd(String zkey, double key, T value) {
        BoundZSetOperations<String, T> operations = redisTemplate.boundZSetOps(zkey);
        operations.add(value, key);
    }

    /**
     * 获取原生K-V 接口
     *
     * @param <T>
     * @return
     */
    public <T> ValueOperations<String, T> opsForValue() {
        ValueOperations<String, T> valueOperations = redisTemplate.opsForValue();
        return valueOperations;
    }

    /**
     * 获取原生 Hash接口
     *
     * @param <T>
     * @return
     */
    public <T> HashOperations<String, String, T> opsForHash() {
        HashOperations<String, String, T> hashOperations = redisTemplate.opsForHash();
        return hashOperations;
    }

    /**
     * 获取原生List接口
     *
     * @param <T>
     * @return
     */
    public <T> ListOperations<String, T> opsForList() {
        ListOperations<String, T> listOperations = redisTemplate.opsForList();
        return listOperations;
    }

    /**
     * 获取原生set接口
     *
     * @param <T>
     * @return
     */
    public <T> SetOperations<String, T> opsForSet() {
        SetOperations<String, T> operations = redisTemplate.opsForSet();
        return operations;
    }

    /**
     * 获取原生zet接口
     *
     * @param <T>
     * @return
     */
    public <T> ZSetOperations<String, T> opsForZSet() {
        ZSetOperations<String, T> operations = redisTemplate.opsForZSet();
        return operations;
    }

    /**
     * 获取原生template
     *
     * @return
     */
    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public void delete(Object key) {
        redisTemplate.delete(key);
    }

    public void deleteHashKey(Object hkey, String key) {
        redisTemplate.opsForHash().delete(hkey, key);
    }
}
