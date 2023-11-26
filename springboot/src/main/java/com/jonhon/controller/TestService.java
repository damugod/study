package com.jonhon.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @version V1.0
 * @Title:
 * @Package com.jonhon
 * @Description：
 * @author: sdm
 * @date: 2023/11/27 1:21 上午
 */
@Service
@Slf4j
public class TestService {
    @Async

    public void async(String id) throws InterruptedException {
      Thread.sleep(10000);
      log.info("async……………………id:{}",id);
    }
}
