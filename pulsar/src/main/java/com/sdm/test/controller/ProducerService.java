package com.sdm.test.controller;

import com.mcd.cn.rdc.pulsar.producer.RdcPulsarTemplate;
import com.sdm.test.User;
import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @version V1.0
 * @Title:
 * @Package com.sdm.test.controller
 * @Description：
 * @author: sdm
 * @date: 2022/11/23 10:58 上午
 */
@Service
public class ProducerService {
    @Autowired
    private RdcPulsarTemplate pulsarTemplate;

    /**
     * 普通队列
     * @throws PulsarClientException
     */
    public void queue() throws PulsarClientException {
        final User sdm = User.builder().name("sdm").age(18).price(BigDecimal.ONE).build();
        pulsarTemplate.send("mcd-test-topic-sample-2",sdm);
    }


    /**
     * 普通队列
     * @throws PulsarClientException
     */
    public void queue2(String name) throws PulsarClientException, ExecutionException, InterruptedException {
        final User sdm = User.builder().name(name).age(18).price(BigDecimal.ONE).build();
        //pulsarTemplate.sendAsync("mcd-retry-test-01",sdm);
        for (int i = 0; i < 1 ; i++) {
            sdm.setAge(i);
            pulsarTemplate.delaySendAsync("mcd-retry-test-02", sdm, "order:key:" + i, 60, TimeUnit.SECONDS);
            pulsarTemplate.delaySendAsync("mcd-retry-test-02", sdm, "order:key:" + i, 2, TimeUnit.SECONDS);

        }
    }


}
