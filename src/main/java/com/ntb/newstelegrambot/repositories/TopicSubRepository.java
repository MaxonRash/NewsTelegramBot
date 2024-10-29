package com.ntb.newstelegrambot.repositories;

import com.ntb.newstelegrambot.repositories.entities.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * {@link Repository} for {@link com.ntb.newstelegrambot.repositories.entities.Topic} entity.
 */
@Repository
public interface TopicSubRepository extends JpaRepository<Topic, Integer> {
    Optional<Topic> findByTopicName(String name);
}
