package com.ntb.newstelegrambot.kafka.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;

@Data
@NoArgsConstructor
public class MapOfNews {
    private LinkedHashMap<String, ListOfNewsObjects> queriesNewsMap;

    public MapOfNews(LinkedHashMap<String, ListOfNewsObjects> queriesNewsMap) {
        this.queriesNewsMap = queriesNewsMap;
    }
}
