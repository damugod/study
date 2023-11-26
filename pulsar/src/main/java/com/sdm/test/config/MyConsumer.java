package com.sdm.test.config;

import com.mcd.cn.rdc.pulsar.CustomerMessage;
import com.mcd.cn.rdc.pulsar.PulsarMessage;
import com.mcd.cn.rdc.pulsar.annotation.RdcPulsarConsumer;
import com.mcd.cn.rdc.pulsar.constant.BatchAckMode;
import com.mcd.cn.rdc.pulsar.constant.RdcConstant;
import com.mcd.cn.rdc.pulsar.properties.RetryProperties;
import com.mcd.cn.rdc.pulsar.utils.JsonUtils;
import com.sdm.test.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.*;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @version V1.0
 * @Title:
 * @Package com.sdm.test.config
 * @Description：
 * @author: sdm
 * @date: 2022/11/23 11:31 上午
 */
@Service
@Slf4j
public class MyConsumer {


//    @RdcPulsarConsumer(topic = "mcd-test-topic-sample-2", clazz = User.class)
//    public void consume(String msg) {
//        // TODO process your message
//        log.info("消费成功 msg={}",msg);
//
//    }
//
//
//    @RdcPulsarConsumer(topic = "mcd-test-topic-sample-2", clazz = User.class)
//    public void consume2(User msg) {
//        // TODO process your message
//        log.info("消费成功 msg={}",msg);
//
//    }

//
//    @RdcPulsarConsumer(topic = "mcd-test-topic-retry", clazz = User.class)
//    public void consume3(User msg) {
//        // TODO process your message
//        log.info("消费成功 msg={}", msg);
//    }

//    @RdcPulsarConsumer(topic = "mcd-test-topic-retry", clazz = User.class)
//    public void consume3(CustomerMessage msg) {
//        final User user = JsonUtils.readValue(msg.getValue(), User.class);
//        // TODO process your message
//        log.info("消费成功 user={} traceId={}", user,msg.getTraceId());
//    }

//    @RdcPulsarConsumer(topic = "mcd-test-topic-retry", clazz = User.class)
//    public void consume3( PulsarMessage<User> message) {
//        final User value = message.getValue();
//        // TODO process your message
//        log.info("消费成功 user={} traceId={}", value);
//    }


//    @RdcPulsarConsumer(topic = "mcd-test-topic-retry", clazz = User.class,batch = true)
//    public void consume3( Messages<User> message) {
//        message.forEach(o->{
//            final MessageId messageId = o.getMessageId();
//            final String key = o.getKey();
//
//            // TODO process your message
//            log.info("消费成功 user={} traceId={}", o.getValue());
//        });
//
//    }

//    @RdcPulsarConsumer(topic = "mcd-test-topic-retry", clazz = CustomerMessage.class,batch = true)
//    public void consume3( Messages<CustomerMessage> message) {
//        message.forEach(o->{
//            final MessageId messageId = o.getMessageId();
//            final String key = o.getKey();
//
//            // TODO process your message
//            log.info("消费成功 user={} traceId={}", o.getValue());
//        });
//
//    }
//    @RdcPulsarConsumer(topic = "mcd-retry-test-02", clazz = User.class,
//            subscriptionType = SubscriptionType.Key_Shared,maxRedeliverCount = 3,initialSubscriptionName = "init-sub"
//            ,batch = true,timeoutMillis = 0
//            ,enableRetry = true,
//            retryStrategy = "1000,5000,10000",
//            retryLetterTopic = "mcd-retry-test-02-retry"
//    )
//    public void consume10(Messages<User> msgs) {
//        log.info("开始消费……");
//        msgs.forEach(o->{
//            final User value = o.getValue();
//            log.info("消费成功,msg={} ", value);
//        });
//




//    }
    @RdcPulsarConsumer(topic = "mcd-retry-test-02", clazz = User.class,
            subscriptionType = SubscriptionType.Key_Shared,maxRedeliverCount = 3,initialSubscriptionName = "init-sub"
            ,enableRetry = true,
            retryStrategy = "1000,5000,10000",
            retryLetterTopic = "mcd-retry-test-02-retry"
    )
    public void consume10(PulsarMessage<User> msg) {
        final Map<String, String> properties = msg.getProperties();
        if(properties.isEmpty()){
            log.info("消费成功,msg={} 需要重试", msg.getValue());
        }else {
            final String s = properties.get(RetryProperties.RECONSUME_TIMES.getKey());
            log.info("消费成功,msg={} 第{}次重试", msg.getValue(),s);

        }
        // TODO process your message

       // throw new RuntimeException("ss");


    }

    @RdcPulsarConsumer(topic = "mcd-retry-test-02-subscription_com.sdm.test.config.MyConsumer_consume10_com.mcd.cn.rdc.pulsar.PulsarMessage<com.sdm.test.User>-DLQ", clazz = User.class,
            subscriptionType = SubscriptionType.Key_Shared,
            subscriptionName = "init-sub",
            batch = true

    )
    public void consume11(Messages<PulsarMessage<User>> msg) {
        msg.forEach(o->{
            final PulsarMessage<User> value = o.getValue();
            final String key = value.getKey();
                    log.info("消费成功");

                }

        );


    }

//    @RdcPulsarConsumer(topic = "mcd-retry-test-01", clazz = User.class,
//            subscriptionType = SubscriptionType.Key_Shared,maxRedeliverCount = 3,initialSubscriptionName = "init-sub"
//            ,enableRetry = true,
//            retryStrategy = "1000,10000,30000",
//            retryLetterTopic = "mcd-retry-test-01-retry"
//    )
//    public void consume10(PulsarMessage<User> msg) {
//        // TODO process your message
//        log.info("消费成功,抛出异常，trace={} msg={}", msg, MDC.get(RdcConstant.TRACE_ID));
//
//
//
//    }


//    @RdcPulsarConsumer(topic = "mcd-test-topic-retry", clazz = CustomerMessage.class, batch = true)
//    public void consume4(Messages<CustomerMessage> messages) {
//        messages.forEach(msg -> {
//            // TODO process your message
//            final CustomerMessage value = msg.getValue();
//            final User user = JsonUtils.readValue(value.getValue(), User.class);
//            log.info("消费成功 user={},traceId={}", user,value.getTraceId());
//                }
//
//        );
//
//    }
//    /**
//     * 延时队列，消费模式必须指定为 Shared 或者 Key_Shared
//     *
//     * @param user
//     */
//    @RdcPulsarConsumer(topic = "mcd-test-topic-delay", clazz = User.class,
//            subscriptionType = SubscriptionType.Shared)
//    public void consume(User user) {
//        // TODO process your message
//
//    }
//
//    @RdcPulsarConsumer(topic = "mcd-test-topic-sample",
//            clazz = User.class,
//            consumerName = "my-consumer",
//            subscriptionName = "my-subscription",
//            batch = true)
//    public void consumeString(Messages<User> msgs) {
//        msgs.forEach((msg) -> {
//            // TODO process your message
//
//        });
//    }
//
//
//    @RdcPulsarConsumer(topic = "mcd-test-topic-sample",
//            clazz = String.class,
//            consumerName = "my-consumer",
//            subscriptionName = "my-subscription",
//            batch = true)
//    public List<MessageId> consumeString2(Messages<String> msgs) {
//        List<MessageId> ackList = new ArrayList<>();
//        msgs.forEach((msg) -> {
//            ackList.add(msg.getMessageId());
//        });
//        return ackList;
//    }
//
//
//    @RdcPulsarConsumer(topic = "mcd-test-topic-sample",
//            clazz=String.class,
//            consumerName = "my-consumer",
//            subscriptionName = "my-subscription",
//            batch = true,
//            batchAckMode = BatchAckMode.MANUAL)
//    public void consumeString3(Messages<String> msgs, Consumer<String> consumer) throws PulsarClientException {
//        List<MessageId> ackList = new ArrayList<>();
//        msgs.forEach((msg) -> {
//            try {
//                ackList.add(msg.getMessageId());
//            } catch (Exception ex) {
//                consumer.negativeAcknowledge(msg);
//            }
//        });
//        consumer.acknowledge(ackList);
//    }
}
