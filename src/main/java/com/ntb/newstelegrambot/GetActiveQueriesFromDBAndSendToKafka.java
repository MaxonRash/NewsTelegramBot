package com.ntb.newstelegrambot;

import com.ntb.newstelegrambot.kafka.KafkaActiveQueriesSender;
import com.ntb.newstelegrambot.kafka.entities.ListOfActiveQueries;
import com.ntb.newstelegrambot.repositories.entities.Topic;
import com.ntb.newstelegrambot.services.TopicSubService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@Slf4j
public class GetActiveQueriesFromDBAndSendToKafka implements CommandLineRunner {
    private TopicSubService topicSubService;
    private KafkaActiveQueriesSender kafkaSender;

    @Autowired
    public GetActiveQueriesFromDBAndSendToKafka(TopicSubService topicSubService, KafkaActiveQueriesSender kafkaSender) {
        this.topicSubService = topicSubService;
        this.kafkaSender = kafkaSender;
    }

    @Override
    public void run(String... args) throws Exception {
        Thread.sleep(5000);
        log.info("Trying to get active topics from DB");
        var activeTopics = topicSubService.findAll().stream().filter(Topic::isActive).map(Topic::getTopicName).toList();
        ListOfActiveQueries listOfActiveQueries = new ListOfActiveQueries(new ArrayList<>(activeTopics));
        kafkaSender.sendMessageToQueriesNewsTopic(listOfActiveQueries);
        log.info("Sent active topics to Kafka. Size is: " + activeTopics.size());
    }
}
