package com.ntb.newstelegrambot.kafka;

import com.ntb.newstelegrambot.kafka.entities.MapOfNews;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaQueriesNewsListener {

    @KafkaListener(/*topicPartitions = @TopicPartition(
            topic = "queriesNews"*//*, partitionOffsets = {@PartitionOffset(partition = "0", initialOffset = "0")}*//*
    ),*/topics = "queriesNews",properties = {"spring.json.value.default.type=com.ntb.newstelegrambot.kafka.entities.MapOfNews"})
    public void consumeTransactionEvent(@Payload MapOfNews newsList
                                        /*@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) Integer key,
                                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                                        @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long timestamp*/) {
        log.info("Received new news");
        try {
            log.info("the size of received map is: " + newsList.getQueriesNewsMap().size());
            KafkaQueueUtil.queue.put(newsList.getQueriesNewsMap());
        } catch (InterruptedException e) {
            log.warn(e.getMessage() , new RuntimeException(e));
        }
        log.info("Added new news to queue");
    }
}