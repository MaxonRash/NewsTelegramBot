package com.ntb.newstelegrambot.kafka;

import com.ntb.newstelegrambot.kafka.entities.ListOfActiveQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaSender {
    private KafkaTemplate<String, ListOfActiveQueries> kafkaTemplate;

    @Autowired
    public KafkaSender(KafkaTemplate<String, ListOfActiveQueries> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topicName, ListOfActiveQueries activeQueries) {
        kafkaTemplate.send(topicName, activeQueries);
    }
}
