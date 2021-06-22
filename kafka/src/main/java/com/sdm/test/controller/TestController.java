package com.sdm.test.controller;

import com.sdm.test.producer.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * @version V1.0
 * @Title:
 * @Package com.sdm.test.controller
 * @Description：
 * @author: sdm
 * @date: 2021/6/15 下午3:00
 */
@RestController
public class TestController {

    public static final String topic = "test-topic-1";

    @Autowired
    private ProducerService producerService ;

    @GetMapping

    public void send () {
        producerService.sendMessageAsync(topic,"test1");
    }

}
