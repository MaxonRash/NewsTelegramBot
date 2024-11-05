package com.ntb.newstelegrambot.kafka;

import com.ntb.newstelegrambot.kafka.entities.ListOfNewsObjects;

import java.util.LinkedHashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class KafkaQueueUtil {
    public static BlockingQueue<LinkedHashMap<String, ListOfNewsObjects>> queue = new LinkedBlockingQueue<>();
}
