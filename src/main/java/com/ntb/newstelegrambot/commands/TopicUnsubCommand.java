package com.ntb.newstelegrambot.commands;

import com.ntb.newstelegrambot.repositories.entities.TelegramUser;
import com.ntb.newstelegrambot.repositories.entities.Topic;
import com.ntb.newstelegrambot.services.SendBotMessageService;
import com.ntb.newstelegrambot.services.TelegramUserService;
import com.ntb.newstelegrambot.services.TopicService;
import com.ntb.newstelegrambot.services.TopicSubService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.ws.rs.NotFoundException;
import java.util.Optional;

@Slf4j
@Component
public class TopicUnsubCommand implements Command{
    TelegramUserService telegramUserService;
    SendBotMessageService sendBotMessageService;
    TopicSubService topicSubService;
    TopicService topicService;

    @Autowired
    public TopicUnsubCommand(TelegramUserService telegramUserService, SendBotMessageService sendBotMessageService,
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
            if (update.getMessage().getText().equalsIgnoreCase(CommandName.REMOVE_TOPIC_SUB.getCommandName())) {
                sendBotMessageService.sendMessage(chatId, "Нужно указать ключевое слово для отмены подписки на новости по нему. Пример: /unsub tesla");
                return;
            }
            String[] messageSplit = update.getMessage().getText().split(StringUtils.SPACE);

            String topicName = messageSplit[1];
            if (StringUtils.isAlpha(topicName)) {
                //if all checks are successful
                var topicOptional = topicSubService.findByTopicName(topicName);
                if (topicOptional.isPresent()) {
                    var topic = topicOptional.get();
                    var newTopic = topicService.getTopicInstance();
                    newTopic.setTopicName(topic.getTopicName());
                    newTopic.setId(topic.getId());
                    newTopic.setActive(topic.isActive());
                    newTopic.setUsers(topic.getUsers());
                    if (topics.stream().map(Topic::getTopicName).toList().contains(newTopic.getTopicName())) {
                        newTopic.removeUser(telegramUser.get());
                        topicSubService.save(newTopic);
                        sendBotMessageService.sendMessage(chatId, "Отписал от новостей по слову " + topicName);
                        log.info("User " + chatId + " unsubscribed from \"" + topicName + "\"");
                        log.info("Sent unsubscribed topic to Kafka. Name: " + topicName);
                    } else {
                        sendBotMessageService.sendMessage(chatId, "Ты не подписан на новости по такому слову. Проверь все свои " +
                                "подписки командой /get_subs");
                    }
                } else {
                    sendBotMessageService.sendMessage(chatId, "Такого ключевого слова в подписках нет. Проверь все свои подписки " +
                            "командой /get_subs");
                }
            } else {
                sendBotMessageService.sendMessage(chatId, "Ключевое слово для отмены подписки должно состоять из букв. Пример: /unsub tesla");
            }

        } else if (telegramUser.isEmpty()) {
            throw new NotFoundException("Юзер " + chatId + " не найден в бд");
        } else {
            sendBotMessageService.sendMessage(chatId, "Работа со мной приостановлена. Чтобы возобновить, нужно написать /start");
        }
    }
}
