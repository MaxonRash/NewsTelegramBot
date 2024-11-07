package com.ntb.newstelegrambot.kafka;

import com.ntb.newstelegrambot.kafka.entities.ListOfActiveQueries;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaActiveQueriesSender extends KafkaSender{
    public KafkaActiveQueriesSender(KafkaTemplate<String, ListOfActiveQueries> kafkaTemplate) {
        super(kafkaTemplate);
    }

    @Override
    public void sendMessage(String topicName, ListOfActiveQueries activeQueries) {
        super.sendMessage(topicName, activeQueries);
    }

    public void sendMessageToQueriesNewsTopic(ListOfActiveQueries activeQueries) {
        super.sendMessage("activeQueries", activeQueries);
    }
}
