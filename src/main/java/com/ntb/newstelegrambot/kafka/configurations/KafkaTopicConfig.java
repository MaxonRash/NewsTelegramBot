package com.ntb.newstelegrambot.kafka.configurations;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic topic1() {
        return TopicBuilder.name("activeQueries").build();
    }

    @Bean
    public NewTopic topic2() {
        return TopicBuilder.name("queriesNews").build();
    }

    @Bean
    public NewTopic topic3() {
        return TopicBuilder.name("removeQuery").build();
    }

}
