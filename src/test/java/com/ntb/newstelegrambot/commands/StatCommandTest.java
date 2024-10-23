package com.ntb.newstelegrambot.commands;

import static com.ntb.newstelegrambot.commands.CommandName.STAT;
import static com.ntb.newstelegrambot.commands.StatCommand.STAT_MESSAGE;

public class StatCommandTest extends AbstractCommandTest {
    @Override
    String getCommandName() {
        return STAT.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return String.format(STAT_MESSAGE, 0);
    }

    @Override
    Command getCommand() {
        return new StatCommand(sendBotMessageService, telegramUserService);
    }
}