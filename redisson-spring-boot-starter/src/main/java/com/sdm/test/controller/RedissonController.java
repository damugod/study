package com.sdm.test.controller;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * All rights Reserved, Designed By dongming.shang
 *
 * @version V1.0
 * @Title:
 * @Package com.sdm.test.controller
 * @Description：
 * @author: sdm
 * @date: 2021/1/10 18:44
 * @Copyright: 2021 R} dongming.shang All rights reserved.
 */
@RestController
public class RedissonController {

    @Autowired
    private RedissonClient redissonClient;

    @GetMapping(value = "/redisson/{key}")
    public String redissonTest(@PathVariable("key") String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            lock.lock();
            Thread.sleep(10000);
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }
        return "已解锁";
    }
}
