package com.ntb.newstelegrambot.repositories.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table
@EqualsAndHashCode(exclude = "users")
public class Topic {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "topic_name")
    private String topicName;

    @Column(name = "active")
    private boolean active;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "topic_x_user",
            joinColumns = @JoinColumn(name = "topic_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<TelegramUser> users;

    @Override
    public String toString() {
        return "Topic{" +
                "id=" + id +
                ", topicName='" + topicName + '\'' +
                ", active=" + active +
                ", users=" + users.stream().map(TelegramUser::getChatId).toList() +
                '}';
    }

    public void addUser(TelegramUser telegramUser) {
        if(Objects.isNull(users)) {
            users = new ArrayList<>();
        }
        users.add(telegramUser);
        active = true;
    }

    public void removeUser(TelegramUser telegramUser) {
        if (Objects.isNull(users)) {
        }
        else {
            users.remove(telegramUser);
            if (users.isEmpty()) {
                users = null;
                active = false;
            }
        }
    }
}
