package com.ntb.newstelegrambot.repositories;

import com.ntb.newstelegrambot.repositories.entities.TelegramUser;
import com.ntb.newstelegrambot.repositories.entities.Topic;
import com.ntb.newstelegrambot.services.TelegramUserService;
import com.ntb.newstelegrambot.services.TopicSubService;
import com.ntb.newstelegrambot.services.TopicSubServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

@DisplayName("Unit-level testing for TopicSubService")
public class TopicSubServiceTest {
    private TopicSubService topicSubService;
    private TopicSubRepository topicSubRepository;
    private TelegramUser newUser;

    private final static String CHAT_ID = "1";

    @BeforeEach
    public void init() {
        TelegramUserService telegramUserService = Mockito.mock(TelegramUserService.class);
        topicSubRepository = Mockito.mock(TopicSubRepository.class);
        topicSubService = new TopicSubServiceImpl(telegramUserService, topicSubRepository);

        newUser = new TelegramUser();
        newUser.setActive(true);
        newUser.setChatId(CHAT_ID);

        Mockito.when(telegramUserService.findByChatId(CHAT_ID)).thenReturn(Optional.of(newUser));
    }

    @Test
    public void shouldProperlySaveTopic() {
        //given

        Topic topic = new Topic();
        topic.setId(1);
        topic.setTopicName("test");

        Topic expectedTopic = new Topic();
        expectedTopic.setTopicName(topic.getTopicName());
        expectedTopic.setId(null);
        expectedTopic.addUser(newUser);
        expectedTopic.setActive(true);

        //when
        topicSubService.save(CHAT_ID, topic.getTopicName());

        //then
        Mockito.verify(topicSubRepository).save(expectedTopic);
    }
    @Test
    public void shouldProperlyAddUserToExistingTopic() {
        //given
        TelegramUser oldTelegramUser = new TelegramUser();
        oldTelegramUser.setChatId("2");
        oldTelegramUser.setActive(true);

        Topic topic = new Topic();
        topic.setId(null);
        topic.setTopicName("test");

        Topic topicFromDB = new Topic();
        topicFromDB.setTopicName(topic.getTopicName());
        topicFromDB.addUser(oldTelegramUser);

        Mockito.when(topicSubRepository.findByTopicName(topic.getTopicName())).thenReturn(Optional.of(topicFromDB));

        Topic expectedTopic = new Topic();
        expectedTopic.setId(topic.getId());
        expectedTopic.setTopicName(topic.getTopicName());
        expectedTopic.addUser(oldTelegramUser);
        expectedTopic.addUser(newUser);

        //when
        topicSubService.save(CHAT_ID, topic.getTopicName());

        //then
        Mockito.verify(topicSubRepository).findByTopicName(topic.getTopicName());
        Mockito.verify(topicSubRepository).save(expectedTopic);
    }

}
