package com.sdm.test.controller;

import com.mcd.cn.rdc.pulsar.producer.RdcPulsarTemplate;
import com.sdm.test.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @version V1.0
 * @Title:
 * @Package PACKAGE_NAME
 * @Description：
 * @author: sdm
 * @date: 2022/11/21 2:55 下午
 */
@RestController
@Slf4j
public class TestController {


    @Autowired
    private ProducerService producerService;

    @GetMapping(value = "/pulsar/{name}")
    public String queue(@PathVariable String name) throws PulsarClientException, ExecutionException, InterruptedException {
        producerService.queue2(name);
      return "success";
    }


//    @GetMapping(value = "/pulsar/delay")
//    public String delayQueue() throws PulsarClientException {
//
//        producerService.delayQueue();
//        return "success";
//
//    }
}
