package com.ntb.newstelegrambot.commands;

import com.ntb.newstelegrambot.services.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.ntb.newstelegrambot.commands.CommandName.*;

/**
 * Help {@link Command}.
 */
public class HelpCommand implements Command {
    private final SendBotMessageService sendBotMessageService;

    public static final String HELP_MESSAGE = String.format("""
                    ✨<b>Доступные команды</b>✨

                    <b>Начать\\закончить работу с ботом</b>
                    %s - начать работу со мной
                    %s - приостановить работу со мной
                    
                    %s - посмотреть статистику
                    
                    ✨<b>Работа с подписками на статьи</b>✨
                    %s - посмотреть все подписки на статьи
                    %s слово - подписаться на новости по этому слову
                    %s слово - отписать от новостей по этому слову
                    %s - отменить все подписки
                    
                    %s - получить помощь в работе со мной
                    """,
            START.getCommandName(), STOP.getCommandName(), STAT.getCommandName(), GET_ALL_SUBS.getCommandName(),
            ADD_TOPIC_SUB.getCommandName(), REMOVE_TOPIC_SUB.getCommandName(), REMOVE_ALL_TOPIC_SUBS.getCommandName(), HELP.getCommandName());

    public HelpCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), HELP_MESSAGE);
    }
}
