package com.ntb.newstelegrambot.commands;

import com.google.common.collect.ImmutableMap;
import com.ntb.newstelegrambot.kafka.KafkaActiveQueriesSender;
import com.ntb.newstelegrambot.services.SendBotMessageService;
import com.ntb.newstelegrambot.services.TelegramUserService;
import com.ntb.newstelegrambot.services.TopicService;
import com.ntb.newstelegrambot.services.TopicSubService;

import static com.ntb.newstelegrambot.commands.CommandName.*;

/**
 * Container of the {@link Command}s, which are using for handling telegram commands.
 */
public class CommandContainer {
    private final ImmutableMap<String, Command> commandMap;
    private final Command unknownCommand;

    public CommandContainer(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService, TopicSubService topicSubService,
                            KafkaActiveQueriesSender kafkaActiveQueriesSender, TopicService topicService) {

        commandMap = ImmutableMap.<String, Command>builder()
                .put(START.getCommandName(), new StartCommand(sendBotMessageService, telegramUserService))
                .put(STOP.getCommandName(), new StopCommand(sendBotMessageService, telegramUserService))
                .put(HELP.getCommandName(), new HelpCommand(sendBotMessageService))
                .put(STAT.getCommandName(), new StatCommand(sendBotMessageService, telegramUserService))
                .put(ADD_TOPIC_SUB.getCommandName(), new AddTopicSubCommand(sendBotMessageService, topicSubService, kafkaActiveQueriesSender))
                .put(GET_ALL_SUBS.getCommandName(), new GetAllSubsCommand(telegramUserService, sendBotMessageService))
                .put(REMOVE_TOPIC_SUB.getCommandName(), new TopicUnsubCommand(telegramUserService, sendBotMessageService, topicSubService, topicService))
                .put(REMOVE_ALL_TOPIC_SUBS.getCommandName(), new TopicUnsubAllCommand(telegramUserService, sendBotMessageService, topicSubService, topicService))
                .put(NO.getCommandName(), new NoCommand(sendBotMessageService))
                .build();

        unknownCommand = new UnknownCommand(sendBotMessageService);
    }

    public Command retrieveCommand(String commandIdentifier) {
        return commandMap.getOrDefault(commandIdentifier, unknownCommand);
    }
}
