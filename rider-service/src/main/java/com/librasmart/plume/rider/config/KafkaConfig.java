package com.librasmart.plume.rider.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic tripRequestedTopic() {
        return TopicBuilder.name(KafkaTopics.TRIP_REQUESTED)
                .partitions(1)
                .replicas(1)
                .build();
    }
}