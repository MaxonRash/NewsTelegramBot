package com.ntb.newstelegrambot;

import com.ntb.newstelegrambot.kafka.KafkaQueueUtil;
import com.ntb.newstelegrambot.kafka.entities.ListOfNewsObjects;
import com.ntb.newstelegrambot.repositories.entities.TelegramUser;
import com.ntb.newstelegrambot.services.SendBotMessageService;
import com.ntb.newstelegrambot.services.TopicSubService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TakeNewsFromQueueAndSendToUsers implements CommandLineRunner {
    private TopicSubService topicSubService;
    private SendBotMessageService sendBotMessageService;

    @Autowired
    public TakeNewsFromQueueAndSendToUsers(TopicSubService topicSubService, SendBotMessageService sendBotMessageService) {
        this.topicSubService = topicSubService;
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void run(String... args) throws Exception {
        //for testing while implementing kafka purposes
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        new Thread() {
            @Override
            public void run() {
                log.info("Thread Taking news started");
                while (true) {
                    LinkedHashMap<String, String> stringMapOfNewsFromQueue = new LinkedHashMap<>();
                    try {
                        log.info("Trying to take news from queue");
                        LinkedHashMap<String, ListOfNewsObjects> newsFromQueue = KafkaQueueUtil.queue.take();
                        log.info("Took news from queue. Size of map is: " + newsFromQueue.size());

                        for (Map.Entry<String, ListOfNewsObjects> mapBefore : newsFromQueue.entrySet()) {
                            stringMapOfNewsFromQueue.put(mapBefore.getKey(),
                                    mapBefore.getValue().getNewsObjectList().stream().limit(10).map(newsObject -> "Заголовок: " + newsObject.getTitle() +
                                            " Ссылка: " + newsObject.getUrl()).collect(Collectors.joining("\r\n", "Последние 10 новостей по теме \""
                        + mapBefore.getKey() + "\" : \r\n", "")));
                        }

                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    LinkedHashMap<List<TelegramUser>, String> userList_x_newsStringList_map = new LinkedHashMap<>();
                    for (Map.Entry<String, String> entry  : stringMapOfNewsFromQueue.entrySet()) {
                        var usersList = topicSubService.findByTopicName(entry.getKey());
                        if (usersList.isPresent()) {
                            userList_x_newsStringList_map.put(usersList.get().getUsers(), entry.getValue());
                        }
                    }
                    userList_x_newsStringList_map.forEach((k,v) -> {
                        k.forEach(user -> sendBotMessageService.sendMessage(user.getChatId(), v));
                    });
                }
            }
        }.start();

    }
}
