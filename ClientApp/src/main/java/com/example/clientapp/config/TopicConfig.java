package com.example.clientapp.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicConfig {
    @Value(value = "${order.topic.name}")
    private String orderTopicName;

    @Value(value = "${notification.topic.name}")
    private String notificationTopicName;

    @Bean
    public NewTopic orderTopic() {
        return TopicBuilder.name(orderTopicName)
                .partitions(3)
                .replicas(2)
                .build();
    }

    @Bean
    public NewTopic notificationTopic() {
        return TopicBuilder.name(notificationTopicName)
                .partitions(3)
                .replicas(2)
                .build();
    }
}
