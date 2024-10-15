package com.ntb.newstelegrambot.commands;

import com.ntb.newstelegrambot.services.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Unknown {@link Command}.
 */
public class UnknownCommand implements Command {
    public static final String UNKNOWN_MESSAGE = "Не знаю эту команду \uD83D\uDE1F, напиши /help чтобы узнать какие есть команды.";

    private final SendBotMessageService sendBotMessageService;

    public UnknownCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), UNKNOWN_MESSAGE);
    }
}
