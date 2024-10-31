package com.ntb.newstelegrambot.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Slf4j
@Component
public class KafkaQueriesNewsListener {

    @KafkaListener(topicPartitions = @TopicPartition(
            topic = "queriesNews", partitionOffsets = {@PartitionOffset(partition = "0", initialOffset = "0")}
    ), properties = {"spring.json.value.default.type=com.ntb.newstelegrambot.kafka.ListOfNewObjects"})
    public void consumeTransactionEvent(@Payload ListOfNewObjects newsList
                                        /*@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) Integer key,
                                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                                        @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long timestamp*/) {
        log.info("Received new news");
        ArrayList<NewsObject> newsObjectArrayList = new ArrayList<>(newsList.newsObjectList);
        newsObjectArrayList.forEach(obj -> {
            try {
                KafkaQueueUtil.queue.put(obj);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        log.info("Drained new list to queue");
    }
}