package com.ntb.newstelegrambot.kafka.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ListOfNewsObjects {
    List<NewsObject> newsObjectList;
}
