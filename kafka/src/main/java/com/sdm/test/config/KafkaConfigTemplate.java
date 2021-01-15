package com.sdm.test.config;

import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.Map;

/**
 * @version V1.0
 * @Title:
 * @Package com.mcd.cn.rdc.mq.kafka.producer
 * @Description： 可以根据需要实现生产者和消费者
 * @author: sdm
 * @date: 2021/1/13 下午3:02
 */
public interface KafkaConfigTemplate {
    /**
     * 生产者配置
     *
     * @return
     */
    Map<String, Object> producerConfigs();

    /**
     * 构建生产者工厂类
     *
     * @return
     */
    ProducerFactory<Integer, String> customizedProducerFactory();

    /**
     * 构建自定义kafkaTemplate
     * @param producerFactory
     * @return
     */
    KafkaTemplate<Integer, String> customizedKafkaTemplate(ProducerFactory producerFactory);


    /**
     * 消费者参数配置
     *
     * @return
     */
    Map<String, Object> consumerConfigs();

    /**
     * 消费者工厂类
     *
     * @return
     */
    ConsumerFactory<Integer, String> customizedConsumerFactory();

    /**
     * 构建并行消费监听容器 多线程消费
     *
     * @param consumerFactory
     * @return
     */
    KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Integer, String>> customizedKafkaListenerContainerFactory(ConsumerFactory<Integer, String> consumerFactory);


}
