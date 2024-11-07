package com.ntb.newstelegrambot.kafka.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
public class ListOfActiveQueries {
    private ArrayList<String> activeQueries;

    public ListOfActiveQueries(ArrayList<String> activeQueries) {
        this.activeQueries = activeQueries;
    }
}
