package com.ntb.newstelegrambot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan(basePackages = {
//        "com.ntb.newstelegrambot.bot",
//        "org.telegram.telegrambots"
//})
public class NewsTelegramBotApplication {

    public static void main(String[] args) {
        //CI configure
        SpringApplication.run(NewsTelegramBotApplication.class, args);
    }

}
