package com.ntb.newstelegrambot.commands;

import com.ntb.newstelegrambot.repositories.entities.TelegramUser;
import com.ntb.newstelegrambot.repositories.entities.Topic;
import com.ntb.newstelegrambot.services.SendBotMessageService;
import com.ntb.newstelegrambot.services.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.ws.rs.NotFoundException;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class GetAllSubsCommand implements Command{
    TelegramUserService telegramUserService;
    SendBotMessageService sendBotMessageService;

    @Autowired
    public GetAllSubsCommand(TelegramUserService telegramUserService, SendBotMessageService sendBotMessageService) {
        this.telegramUserService = telegramUserService;
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        Optional<TelegramUser> telegramUser = telegramUserService.findByChatId(String.valueOf(update.getMessage().getChatId()));
        if (telegramUser.isPresent() && telegramUser.get().isActive()) {
            var topics = telegramUser.get().getTopics();
            if (topics.isEmpty()) {
                sendBotMessageService.sendMessage(String.valueOf(update.getMessage().getChatId()), "У тебя пока нет активных подписок");
            }
            else {
                sendBotMessageService.sendMessage(String.valueOf(update.getMessage().getChatId()),
                        topics.stream().map(Topic::getTopicName).collect(Collectors.joining(" ; ",
                                "Все ключевые слова, на которые ты подписан: \r\n", "")));
            }
        } else if (telegramUser.isEmpty()) {
            throw new NotFoundException("Юзер " + update.getMessage().getChatId() + " не найден в бд");
        } else {
            sendBotMessageService.sendMessage(String.valueOf(update.getMessage().getChatId()), "Работа со мной приостановлена. Чтобы возобновить, нужно написать /start");
        }
    }
}
