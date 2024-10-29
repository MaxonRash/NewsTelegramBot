package com.ntb.newstelegrambot.bot;

import com.ntb.newstelegrambot.commands.CommandContainer;
import com.ntb.newstelegrambot.services.SendBotMessageServiceImpl;
import com.ntb.newstelegrambot.services.TelegramUserService;
import com.ntb.newstelegrambot.services.TopicSubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.ntb.newstelegrambot.commands.CommandName.NO;

@Component
public class NewsTelegramBot extends TelegramLongPollingBot {
    public static String COMMAND_PREFIX = "/";
    @Value("${bot.username}")
    private String username;
    @Value("${bot.token}")
    private String botToken;
    private final CommandContainer commandContainer;

    @Autowired
    public NewsTelegramBot(TelegramUserService telegramUserService, TopicSubService topicSubService) {
        this.commandContainer = new CommandContainer(new SendBotMessageServiceImpl(this), telegramUserService, topicSubService);
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText().trim();
            if (message.startsWith(COMMAND_PREFIX)) {
                String commandIdentifier = message.split(" ")[0].toLowerCase();

                commandContainer.retrieveCommand(commandIdentifier).execute(update);
            } else {
                commandContainer.retrieveCommand(NO.getCommandName()).execute(update);
            }
        }
    }
}
