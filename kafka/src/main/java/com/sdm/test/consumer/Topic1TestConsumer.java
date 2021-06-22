package com.sdm.test.consumer;



import com.sdm.test.config.KafkaConfig1;
import com.sdm.test.controller.TestController;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;


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


    @KafkaListener(topics ="${topic.demo}" ,
            containerFactory = KafkaConfig1.LISTENER_CONTAINER_FACTORY)
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
