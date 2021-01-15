package com.sdm.test.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @version V1.0
 * @Title:
 * @Package com.sdm.test.config
 * @Description：
 * @author: sdm
 * @date: 2021/1/15 上午10:44
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class KafkaDemo1Test {

    public static final String topic_1 =  "point-push-ali-topic-dev";

    @Autowired
    @Qualifier(KafkaDemo1.KAFKA_TEMPLATE)
    private KafkaTemplate kafkaTemplate ;
    @Test
    public void  producer(){
        kafkaTemplate.send(topic_1,"one");
        System.out.println("写入kafka成功");

    }
}