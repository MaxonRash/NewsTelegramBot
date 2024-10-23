package com.ntb.newstelegrambot.commands;

import com.ntb.newstelegrambot.services.SendBotMessageService;
import com.ntb.newstelegrambot.services.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Statistics {@link Command}.
 */
public class StatCommand implements Command {

    private final TelegramUserService telegramUserService;
    private final SendBotMessageService sendBotMessageService;

    public final static String STAT_MESSAGE = "News Telegram Bot использует %s человек.";

    public StatCommand(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute(Update update) {
        int activeUserCount = telegramUserService.retrieveAllActiveUsers().size();
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), String.format(STAT_MESSAGE, activeUserCount));
    }
}