package com.sdm.test.config;

import lombok.Getter;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * @version V1.0
 * @Title:
 * @Package com.sdm.test.config
 * @Description：
 * @author: sdm
 * @date: 2021/1/13 下午3:57
 */
@Configuration
@ConditionalOnProperty(name = {"bootstrap-servers"}, prefix = "customized.kafka.mt")
public class KafkaDemo1 implements KafkaConfigTemplate {
    @Value("${customized.kafka.mt.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${customized.kafka.mt.consumer.auto-offset-reset}")
    private String autoOffsetReset;

    @Value("${customized.kafka.mt.producer.retries}")
    private int retries;

    @Value("${customized.kafka.mt.producer.buffer-memory}")
    private long bufferMemory;

    @Value("${customized.kafka.mt.producer.batch-size}")
    private Integer batchSize;

    @Value("${customized.kafka.mt.linger-ms:1000}")
    private Integer lingerMs;


    public static final String beanName = "kafka1config" ;





    @Override
    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>(10);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.RETRIES_CONFIG, retries);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
        props.put(ProducerConfig.LINGER_MS_CONFIG, lingerMs);

        return props;
    }

    @Override
    @Bean
    public ProducerFactory<Integer, String> producerFactory() {
        Map<String, Object> configs = producerConfigs();

        DefaultKafkaProducerFactory<Integer, String> producerFactory = new DefaultKafkaProducerFactory(configs, new IntegerSerializer(), new StringSerializer());
        return producerFactory;
    }

    @Override
    @Bean(beanName)
    public KafkaTemplate<Integer, String> kafkaTemplate() {
        return new KafkaTemplate(producerFactory());
    }

    @Override
    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>(10);

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        //设置是否自动提交offset 2.3 版本以后默认为false
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 200000);

        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);

        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return props;
    }

    @Override
    @Bean
    public ConsumerFactory<Integer, String> consumerFactory() {
        IntegerDeserializer keyDeserializer = new IntegerDeserializer();

        StringDeserializer valueDeserializer = new StringDeserializer();

        ConsumerFactory<Integer, String> consumerFactory = new DefaultKafkaConsumerFactory(consumerConfigs(), keyDeserializer, valueDeserializer);

        return consumerFactory;    }

    @Override
    @Bean("kafkaListenerContainerFactory1")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Integer, String>> kafkaListenerContainerFactory(@Qualifier("consumerFactory")ConsumerFactory<Integer, String> consumerFactory) {
        //构建kafka并行消费监听类工厂类 此类通过topic名称创建该topic消费监听
        ConcurrentKafkaListenerContainerFactory<Integer, String> concurrentKafkaListenerContainerFactory =
                new ConcurrentKafkaListenerContainerFactory<>();
        //可通过注解的方式进行设置
        concurrentKafkaListenerContainerFactory.setConsumerFactory(consumerFactory);
        //
        concurrentKafkaListenerContainerFactory.getContainerProperties().setAckOnError(false);

        //设置ack模型机制 当发生error时 不同处理机制针对与offset有不同处理机制
        concurrentKafkaListenerContainerFactory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        return concurrentKafkaListenerContainerFactory;
    }
}
