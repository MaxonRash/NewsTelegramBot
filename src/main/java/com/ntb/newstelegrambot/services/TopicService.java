package com.ntb.newstelegrambot.services;

import com.ntb.newstelegrambot.repositories.entities.Topic;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicService {
    private ObjectFactory<Topic> objectFactory;

    @Autowired
    private TopicService(ObjectFactory<Topic> objectFactory) {
        this.objectFactory = objectFactory;
    }

    public Topic getTopicInstance() {
        return objectFactory.getObject();
    }
}
