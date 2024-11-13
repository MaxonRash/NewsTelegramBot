package com.ntb.newstelegrambot.commands;

import com.ntb.newstelegrambot.repositories.entities.TelegramUser;
import com.ntb.newstelegrambot.repositories.entities.Topic;
import com.ntb.newstelegrambot.services.SendBotMessageService;
import com.ntb.newstelegrambot.services.TelegramUserService;
import com.ntb.newstelegrambot.services.TopicService;
import com.ntb.newstelegrambot.services.TopicSubService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.ws.rs.NotFoundException;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TopicUnsubAllCommand implements Command{
    TelegramUserService telegramUserService;
    SendBotMessageService sendBotMessageService;
    TopicSubService topicSubService;
    TopicService topicService;

    @Autowired
    public TopicUnsubAllCommand(TelegramUserService telegramUserService, SendBotMessageService sendBotMessageService,
                             TopicSubService topicSubService, TopicService topicService) {
        this.telegramUserService = telegramUserService;
        this.sendBotMessageService = sendBotMessageService;
        this.topicSubService = topicSubService;
        this.topicService = topicService;
    }
    @Override
    public void execute(Update update) {
        Optional<TelegramUser> telegramUser = telegramUserService.findByChatId(String.valueOf(update.getMessage().getChatId()));
        String chatId = update.getMessage().getChatId().toString();
        if (telegramUser.isPresent() && telegramUser.get().isActive()) {
            var topics = telegramUser.get().getTopics();
            if (topics.isEmpty()) {
                sendBotMessageService.sendMessage(chatId, "У тебя пока нет активных подписок");
                return;
            }
            var modifiedTopics = topics.stream().map(oldTopic -> {
                Topic newTopic = topicService.getTopicInstance();
                newTopic.setTopicName(oldTopic.getTopicName());
                newTopic.setId(oldTopic.getId());
                newTopic.setActive(oldTopic.isActive());
                newTopic.setUsers(oldTopic.getUsers());
                return newTopic;
            }).toList();
            modifiedTopics.forEach(topic -> topic.removeUser(telegramUser.get()));

            modifiedTopics.forEach(topic1 -> topicSubService.save(topic1));
            sendBotMessageService.sendMessage(chatId, "Удалил все твои подписки, а именно: " + topics.stream().map(Topic::getTopicName).collect(Collectors.joining("\" ; \"", "\"", "\"")));
            log.info("User " + chatId + " unsubscribed from " + topics.stream().map(Topic::getTopicName).collect(Collectors.joining("\" ; \"", "\"", "\"")));
            log.info("Sent unsubscribed topics to Kafka. Names: " + topics.stream().map(Topic::getTopicName).collect(Collectors.joining("\" ; \"", "\"", "\"")));
        } else if (telegramUser.isEmpty()) {
            throw new NotFoundException("Юзер " + chatId + " не найден в бд");
        } else {
            sendBotMessageService.sendMessage(chatId, "Работа со мной приостановлена. Чтобы возобновить, нужно написать /start");
        }

    }
}
