package com.ntb.newstelegrambot.services;

import com.ntb.newstelegrambot.repositories.entities.Topic;

import java.util.List;
import java.util.Optional;

public interface TopicSubService {
    Topic save(String chatId, String topicName);
    Optional<Topic> findByTopicName(String topicName);
    List<Topic> findAll();
}
