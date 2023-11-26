package com.sdm.test.consumer;

import com.sdm.test.config.KafkaConfig1;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @version V1.0
 * @Title:
 * @Package com.sdm.test.consumer
 * @Description：
 * @author: sdm
 * @date: 2021/6/22 下午2:21
 */
@Component
@Slf4j
public class BatchConsumer {
    // 每个线程处理的最大数量
    private static final int MAX_NUM = 100;

    private static ExecutorService executorService = new ThreadPoolExecutor(MAX_NUM, MAX_NUM,
            0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), Executors.defaultThreadFactory());



    @KafkaListener(topics ="${topic.demo}" ,
            containerFactory = KafkaConfig1.LISTENER_CONTAINER_FACTORY_BATCH)
    public void loadListener(List<ConsumerRecord<?, ?>> record, Acknowledgment ack) {
        final long startTime = System.currentTimeMillis();
        int batchSize = record.size() / MAX_NUM;
        //该消费者每次消费时都积压了大量消息，提交offset时要保证所有异步线程处理完毕
        if (batchSize == 0) {
            // 消息小于100
            this.dealMessage(record);
        } else {
            LinkedList<Future> futures = new LinkedList<>();
            for (int i = 0; i < batchSize; i++) {
                List<ConsumerRecord<?, ?>> records;
                if (i == batchSize - 1) {
                    //需要把余数加上
                    records = record.subList(i * MAX_NUM, record.size());
                } else {
                    records = record.subList(i * MAX_NUM, (i + 1) * MAX_NUM);
                }
                final Future<?> submit = executorService.submit(() -> {
                    this.dealMessage(records);
                });
                futures.add(submit);
            }
            //等待线程全部执行完
            for (Future future : futures) {
                try {
                    future.get();
                } catch (InterruptedException e) {
                    // ignore
                } catch (ExecutionException e) {
                    // ignore
                }
            }

        }
        log.info("批量处理完成,处理数量={},耗时={}ms", record.size(), System.currentTimeMillis() - startTime);
        //手动确定 提交offset
        ack.acknowledge();
    }

    /**
     * 批量处理
     * @param record
     */
    public void dealMessage(List<ConsumerRecord<?, ?>> record) {
        for (ConsumerRecord<?, ?> consumerRecord : record) {
            //业务逻辑
            this.dealMessage(consumerRecord);
        }
    }

    private void dealMessage(ConsumerRecord<?,?> consumerRecord) {
        try {
            log.info("单条处理");
        } catch (Exception e) {
            //
        }
    }


}
