package com.ntb.newstelegrambot.commands;

import org.junit.jupiter.api.DisplayName;

import static com.ntb.newstelegrambot.commands.CommandName.HELP;
import static com.ntb.newstelegrambot.commands.HelpCommand.HELP_MESSAGE;

@DisplayName("Unit-level testing for HelpCommand")
public class HelpCommandTest extends AbstractCommandTest {
    @Override
    String getCommandName() {
        return HELP.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return HELP_MESSAGE;
    }

    @Override
    Command getCommand() {
        return new HelpCommand(sendBotMessageService);
    }
}
