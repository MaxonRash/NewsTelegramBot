package com.ntb.newstelegrambot.commands;
/**
 * Enumeration for {@link Command}'s.
 */
public enum CommandName {
    START("/start"),
    STOP("/stop"),
    HELP("/help"),
    STAT("/stat"),
    ADD_TOPIC_SUB("/sub"),
    GET_ALL_SUBS("/get_subs"),
    REMOVE_TOPIC_SUB("/unsub"),
    REMOVE_ALL_TOPIC_SUBS("/unsub_all"),
    NO("nocommand");
    private final String commandName;
    CommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}
