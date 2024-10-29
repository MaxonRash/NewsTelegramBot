package com.ntb.newstelegrambot.services;

import com.ntb.newstelegrambot.repositories.TopicSubRepository;
import com.ntb.newstelegrambot.repositories.entities.TelegramUser;
import com.ntb.newstelegrambot.repositories.entities.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.Optional;

@Service
public class TopicSubServiceImpl implements TopicSubService {

    private final TelegramUserService telegramUserService;
    private final TopicSubRepository topicRepository;

    @Autowired
    public TopicSubServiceImpl(TelegramUserService telegramUserService, TopicSubRepository topicRepository) {
        this.telegramUserService = telegramUserService;
        this.topicRepository = topicRepository;
    }

    @Override
    public Optional<Topic> findByTopicName(String topicName) {
        return topicRepository.findByTopicName(topicName);
    }

    @Override
    public Topic save(String chatId, String topicName) {
        TelegramUser telegramUser = telegramUserService.findByChatId(chatId).orElseThrow(NotFoundException::new);
        Topic topic;
        Optional<Topic> topicFromDB = topicRepository.findByTopicName(topicName);
        if (topicFromDB.isPresent()) {
            topic = topicFromDB.get();
            var user = topic.getUsers().stream().filter(tu -> tu.getChatId().equalsIgnoreCase(chatId)).findFirst();
            if (user.isEmpty()) {
                topic.addUser(telegramUser);
                topic.setActive(true);
            }
        } else {
            topic = new Topic();
            topic.addUser(telegramUser);
            topic.setTopicName(topicName);
            topic.setActive(true);
        }
        return topicRepository.save(topic);
    }
}