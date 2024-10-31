package com.ntb.newstelegrambot.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ListOfNewObjects {
    List<NewsObject> newsObjectList;
}
