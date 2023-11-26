package com.sdm.test.config;

import com.mcd.cn.rdc.pulsar.producer.ProducerFactory;
import com.sdm.test.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @version V1.0
 * @Title:
 * @Package com.sdm.test.config
 * @Description：
 * @author: sdm
 * @date: 2022/11/21 3:13 下午
 */


@Configuration
public class Producer {

    @Bean
    public ProducerFactory producerFactory() {
        return new ProducerFactory()
                .addProducer("mcd-retry-test-02", User.class)
                ;
    }

}

