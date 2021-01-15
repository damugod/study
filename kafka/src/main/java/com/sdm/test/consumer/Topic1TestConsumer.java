package com.sdm.test.consumer;

import com.sdm.test.config.KafkaDemo1;

import com.sdm.test.config.KafkaDemo1Test;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionException;
import org.springframework.util.Assert;

import java.util.Optional;

/**
 * @version V1.0
 * @Title:
 * @Package com.sdm.test.consumer
 * @Description：
 * @author: sdm
 * @date: 2021/1/15 上午10:51
 */
@Component
public class Topic1TestConsumer {


    @KafkaListener(groupId = "${customized.kafka.mt.consumer.group-id}",
            topics = KafkaDemo1Test.topic_1,
            containerFactory = "kafkaListenerContainerFactory")
    public void loadListener(ConsumerRecord<?, ?> record, Acknowledgment ack) {

        try {
            System.out.println("监听到topic1的消息");
        } catch (Exception e) {

        } finally {
            //手动确定 提交offset
            ack.acknowledge();
        }
    }
}
