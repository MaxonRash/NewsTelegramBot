package com.ntb.newstelegrambot.repositories.entities;

import com.ntb.newstelegrambot.kafka.KafkaRemoveQuerySender;
import com.ntb.newstelegrambot.kafka.entities.RemoveQueryObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@Scope("prototype")
@Data
@NoArgsConstructor
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

    @Transient
    @Autowired
    KafkaRemoveQuerySender kafkaRemoveQuerySender;

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
                kafkaRemoveQuerySender.sendMessage(new RemoveQueryObject(this.topicName));
                active = false;
            }
        }
    }
}
