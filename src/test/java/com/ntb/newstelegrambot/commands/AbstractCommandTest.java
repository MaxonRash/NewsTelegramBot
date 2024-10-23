package com.ntb.newstelegrambot.commands;

import com.ntb.newstelegrambot.bot.NewsTelegramBot;
import com.ntb.newstelegrambot.services.SendBotMessageService;
import com.ntb.newstelegrambot.services.SendBotMessageServiceImpl;
import com.ntb.newstelegrambot.services.TelegramUserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Abstract class for testing {@link Command}s.
 */
abstract class AbstractCommandTest {

    protected NewsTelegramBot newsTelegramBot = Mockito.mock(NewsTelegramBot.class);
    protected SendBotMessageService sendBotMessageService = new SendBotMessageServiceImpl(newsTelegramBot);
    protected TelegramUserService telegramUserService = Mockito.mock(TelegramUserService.class);

    abstract String getCommandName();

    abstract String getCommandMessage();

    abstract Command getCommand();

    @Test
    public void shouldProperlyExecuteCommand() throws TelegramApiException {
        //given
        Long chatId = 1234567824356L;

        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(chatId);
        Mockito.when(message.getText()).thenReturn(getCommandName());
        update.setMessage(message);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(getCommandMessage());
        sendMessage.enableHtml(true);

        //when
        getCommand().execute(update);

        //then
        Mockito.verify(newsTelegramBot).execute(sendMessage);
    }
}