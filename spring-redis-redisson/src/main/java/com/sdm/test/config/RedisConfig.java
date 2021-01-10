package com.sdm.test.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * All rights Reserved, Designed By dongming.shang
 *
 * @version V1.0
 * @Title:
 * @Package com.sdm.test.config
 * @Description：
 * @author: sdm
 * @date: 2021/1/10 13:12
 * @Copyright: 2021 R} dongming.shang All rights reserved.
 */
@Configuration
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfig {

    @Bean
    @Primary
    public RedissonConnectionFactory redissonConnectionFactory (RedissonClient redissonClient){
        return new RedissonConnectionFactory(redissonClient);
    }

    @Bean(destroyMethod ="shutdown")
    @Primary
    public RedissonClient redissonClient(RedisProperties redisProperties){

        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://"+redisProperties.getHost()+":"+redisProperties.getPort())
                .setPassword(redisProperties.getPassword())
                .setConnectionPoolSize(100)
                .setConnectionMinimumIdleSize(10);
        return Redisson.create(config);
    }
}
