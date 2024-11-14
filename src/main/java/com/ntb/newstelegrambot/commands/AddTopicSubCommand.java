package com.ntb.newstelegrambot.commands;

import com.ntb.newstelegrambot.kafka.KafkaActiveQueriesSender;
import com.ntb.newstelegrambot.kafka.entities.ListOfActiveQueries;
import com.ntb.newstelegrambot.services.SendBotMessageService;
import com.ntb.newstelegrambot.services.TopicSubService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Add Topic subscription {@link Command}.
 */
@Slf4j
public class AddTopicSubCommand implements Command {
    private final KafkaActiveQueriesSender kafkaActiveQueriesSender;

    private final SendBotMessageService sendBotMessageService;
    private final TopicSubService topicSubService;

    public AddTopicSubCommand(SendBotMessageService sendBotMessageService, TopicSubService topicSubService, KafkaActiveQueriesSender kafkaActiveQueriesSender) {
        this.sendBotMessageService = sendBotMessageService;
        this.topicSubService = topicSubService;
        this.kafkaActiveQueriesSender = kafkaActiveQueriesSender;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        String[] messageSplit = update.getMessage().getText().split(StringUtils.SPACE);
        if (update.getMessage().getText().equalsIgnoreCase(CommandName.ADD_TOPIC_SUB.getCommandName())) {
            sendBotMessageService.sendMessage(chatId, "Нужно указать ключевое слово для подписки на новости по нему. Пример: /sub tesla");
            return;
        }
        String topicName = messageSplit[1].toLowerCase();
        if (StringUtils.isAlpha(topicName)) {
            topicSubService.save(chatId, topicName);
            sendBotMessageService.sendMessage(chatId, String.format("Подписал на новости по \"%s\"", topicName));
            log.info(String.format("User %s subscribed for \"%s\"", chatId, topicName));
            ListOfActiveQueries list = new ListOfActiveQueries();
            list.setActiveQueries(new ArrayList<>(Collections.singleton(topicName)));
            kafkaActiveQueriesSender.sendMessageToQueriesNewsTopic(list);
            log.info("Sent new topic with name \"" + topicName + "\" to kafka activeQueries");
        } else {
            sendBotMessageService.sendMessage(chatId, "Ключевое слово для подписки должно состоять из букв. Пример: /sub tesla");
        }
    }

}
