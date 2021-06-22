package com.sdm.test.producer;

import com.alibaba.fastjson.JSON;
import com.sdm.test.config.KafkaConfig1;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONUtil;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @version V1.0
 * @Title:
 * @Package com.sdm.test.producer
 * @Description：
 * @author: sdm
 * @date: 2021/6/15 上午10:27
 */
@Component
@Slf4j
public class ProducerService {

    @Autowired
    @Qualifier(KafkaConfig1.KAFKA_TEMPLATE)
    private KafkaTemplate kafkaTemplate ;
    /**
     * producer 同步方式发送数据
     * @param topic   topic名称
     * @param message producer发送的数据
     */
    public void sendMessageSync(String topic, String key,String message) throws InterruptedException, ExecutionException, TimeoutException {
        kafkaTemplate.send(topic,key,message).get(1, TimeUnit.SECONDS);
    }

    /**
     * producer 异步方式发送数据
     * @param topic topic名称
     * @param t     producer发送的数据
     */
    @KafkaListener
    public <T> void sendMessageAsync(String topic, String key,T t) {

        Map<String, String> contextMap = MDC.getCopyOfContextMap();
        Message<String> message = MessageBuilder
                .withPayload(JSON.toJSONString(t))
                .setHeader(KafkaHeaders.TOPIC, topic)
                .setHeader(KafkaHeaders.MESSAGE_KEY,key)

                .build();

        kafkaTemplate.send(message).addCallback(new ListenableFutureCallback() {
            @Override
            public void onFailure(Throwable throwable) {
                log.error("发送topic{}产生异常信息:{}", topic, throwable);
            }
            @Override
            public void onSuccess(Object o) {

            }
        });
    }

    /**
     * producer 同步方式发送数据
     * @param topic   topic名称
     * @param message producer发送的数据
     */
    public void sendMessageSync(String topic, String message) throws InterruptedException, ExecutionException, TimeoutException {
        kafkaTemplate.send(topic,message).get(1, TimeUnit.SECONDS);
    }

    /**
     * producer 异步方式发送数据
     * @param topic topic名称
     * @param t     producer发送的数据
     */
    public <T> void sendMessageAsync(String topic, T t) {
        Map<String, String> contextMap = MDC.getCopyOfContextMap();
        Message<String> message = MessageBuilder
                .withPayload(JSON.toJSONString(t))
                .setHeader(KafkaHeaders.TOPIC, topic)
                .build();
        kafkaTemplate.send(message).addCallback(new ListenableFutureCallback() {
            @Override
            public void onFailure(Throwable throwable) {
                log.error("发送topic{}产生异常信息:{}", topic, throwable);
            }
            @Override
            public void onSuccess(Object o) {

            }
        });
    }
}
