package com.ntb.newstelegrambot.commands;

import com.ntb.newstelegrambot.repositories.entities.TelegramUser;
import com.ntb.newstelegrambot.repositories.entities.Topic;
import com.ntb.newstelegrambot.services.SendBotMessageService;
import com.ntb.newstelegrambot.services.TelegramUserService;
import com.ntb.newstelegrambot.services.TopicSubService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

import static com.ntb.newstelegrambot.commands.CommandName.ADD_TOPIC_SUB;

@DisplayName("Unit-level testing for AddTopicCommand")
public class AddTopicSubCommandTest {
    private SendBotMessageService sendBotMessageService;
    private TelegramUser telegramUser;
    private Topic topic;
    private AddTopicSubCommand addTopicSubCommand;
    @BeforeEach
    public void init() {
        TopicSubService topicSubService = Mockito.mock(TopicSubService.class);
        sendBotMessageService = Mockito.mock(SendBotMessageService.class);
        TelegramUserService telegramUserService = Mockito.mock(TelegramUserService.class);

        addTopicSubCommand = new AddTopicSubCommand(sendBotMessageService, topicSubService);

        telegramUser = new TelegramUser();
        telegramUser.setChatId("1");
        telegramUser.setActive(true);

        topic = new Topic();
        topic.setTopicName("test");
        topic.setId(null);

        Mockito.when(telegramUserService.findByChatId(telegramUser.getChatId())).thenReturn(Optional.of(telegramUser));
    }

    @Test
    public void shouldProperlyAnswerWhenSubscribedUserToTopic() {
        //given
        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(Long.valueOf(telegramUser.getChatId()));
        Mockito.when(message.getText()).thenReturn(ADD_TOPIC_SUB.getCommandName() + " test");
        update.setMessage(message);

        String messageToSend = String.format("Подписал на новости по %s", topic.getTopicName());

        //when
        addTopicSubCommand.execute(update);

        //then
        Mockito.verify(sendBotMessageService).sendMessage(telegramUser.getChatId(), messageToSend);
    }

    @Test
    public void shouldProperlyAnswerWhenTopicNameIsNotSpecified() {
        //given
        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(Long.valueOf(telegramUser.getChatId()));
        Mockito.when(message.getText()).thenReturn(ADD_TOPIC_SUB.getCommandName());
        update.setMessage(message);

        String messageToSend = "Нужно указать ключевое слово для подписки на новости по нему. Пример: /topicsub tesla";

        //when
        addTopicSubCommand.execute(update);

        //then
        Mockito.verify(sendBotMessageService).sendMessage(telegramUser.getChatId(), messageToSend);
    }

    @Test
    public void shouldProperlyAnswerWhenTopicNameIsNotAlphabetic() {
        //given
        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(Long.valueOf(telegramUser.getChatId()));
        Mockito.when(message.getText()).thenReturn(ADD_TOPIC_SUB.getCommandName() + " 123asd");
        update.setMessage(message);

        String messageToSend = "Ключевое слово для подписки должно состоять из букв. Пример: /topicsub tesla";

        //when
        addTopicSubCommand.execute(update);

        //then
        Mockito.verify(sendBotMessageService).sendMessage(telegramUser.getChatId(), messageToSend);
    }
}
