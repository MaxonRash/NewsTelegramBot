package com.ntb.newstelegrambot.kafka;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class KafkaQueueUtil {
    public static BlockingQueue<NewsObject> queue = new LinkedBlockingQueue<>();
}
