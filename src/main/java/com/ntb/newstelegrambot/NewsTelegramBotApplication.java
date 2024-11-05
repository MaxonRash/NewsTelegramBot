package com.ntb.newstelegrambot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
//for SpringBoot3 component scan below must be present for keep app running
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
