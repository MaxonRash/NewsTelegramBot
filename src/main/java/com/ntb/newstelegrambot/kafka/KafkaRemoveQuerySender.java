package com.ntb.newstelegrambot.kafka;

import com.ntb.newstelegrambot.kafka.entities.RemoveQueryObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaRemoveQuerySender {
    private KafkaTemplate<String, RemoveQueryObject> kafkaTemplate;

    @Autowired
    public KafkaRemoveQuerySender(KafkaTemplate<String, RemoveQueryObject> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(RemoveQueryObject removeQuery) {
        kafkaTemplate.send("removeQuery", removeQuery);
    }
}
