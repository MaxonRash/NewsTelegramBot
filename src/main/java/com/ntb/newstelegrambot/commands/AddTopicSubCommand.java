package com.ntb.newstelegrambot.commands;

import com.ntb.newstelegrambot.repositories.entities.Topic;
import com.ntb.newstelegrambot.services.SendBotMessageService;
import com.ntb.newstelegrambot.services.TopicSubService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

/**
 * Add Topic subscription {@link Command}.
 */
@Slf4j
public class AddTopicSubCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final TopicSubService topicSubService;

    public AddTopicSubCommand(SendBotMessageService sendBotMessageService, TopicSubService topicSubService) {
        this.sendBotMessageService = sendBotMessageService;
        this.topicSubService = topicSubService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        String[] messageSplit = update.getMessage().getText().split(StringUtils.SPACE);
        if (update.getMessage().getText().equalsIgnoreCase(CommandName.ADD_TOPIC_SUB.getCommandName())) {
            sendBotMessageService.sendMessage(chatId, "Нужно указать ключевое слово для подписки на новости по нему. Пример: /topicsub tesla");
            return;
        }
        String topicName = messageSplit[1];
        if (StringUtils.isAlpha(topicName)) {
            topicSubService.save(chatId, topicName);
            sendBotMessageService.sendMessage(chatId, String.format("Подписал на новости по %s", topicName));
            log.info(String.format("User %s subscribed for %s", chatId, topicName));
        } else {
            sendBotMessageService.sendMessage(chatId, "Ключевое слово для подписки должно состоять из букв. Пример: /topicsub tesla");
        }
    }

}