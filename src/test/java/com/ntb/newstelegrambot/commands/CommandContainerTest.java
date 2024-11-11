package com.ntb.newstelegrambot.commands;

import com.ntb.newstelegrambot.kafka.KafkaActiveQueriesSender;
import com.ntb.newstelegrambot.services.SendBotMessageService;
import com.ntb.newstelegrambot.services.TelegramUserService;
import com.ntb.newstelegrambot.services.TopicService;
import com.ntb.newstelegrambot.services.TopicSubService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

@DisplayName("Unit-level testing for CommandContainer")
public class CommandContainerTest {
    private CommandContainer commandContainer;

    @BeforeEach
    public void init() {
        SendBotMessageService sendBotMessageService = Mockito.mock(SendBotMessageService.class);
        TelegramUserService telegramUserService = Mockito.mock(TelegramUserService.class);
        TopicSubService topicSubService = Mockito.mock(TopicSubService.class);
        KafkaActiveQueriesSender kafkaActiveQueriesSender = Mockito.mock(KafkaActiveQueriesSender.class);
        TopicService topicService = Mockito.mock(TopicService.class);
        commandContainer = new CommandContainer(sendBotMessageService, telegramUserService, topicSubService, kafkaActiveQueriesSender, topicService);
    }

    @Test
    public void shouldGetAllTheExistingCommands() {
        //when-then
        Arrays.stream(CommandName.values())
                .forEach(commandName -> {
                    Command command = commandContainer.retrieveCommand(commandName.getCommandName());
                    Assertions.assertNotEquals(UnknownCommand.class, command.getClass());
                });
    }

    @Test
    public void shouldReturnUnknownCommand() {
        //given
        String unknownCommand = "/fgjhdfgdfg";

        //when
        Command command = commandContainer.retrieveCommand(unknownCommand);

        //then
        Assertions.assertEquals(UnknownCommand.class, command.getClass());
    }
}
