package com.ntb.newstelegrambot.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NewsObject {
    private String sourceName;
    private String title;
    private String url;
    private String publishedAt;
}
