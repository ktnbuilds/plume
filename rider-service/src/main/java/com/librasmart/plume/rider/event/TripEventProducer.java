package com.librasmart.plume.rider.event;

import com.librasmart.plume.rider.config.KafkaTopics;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TripEventProducer {

    private static final String TOPIC = KafkaTopics.TRIP_REQUESTED;

    private final KafkaTemplate<String, TripRequestedEvent> kafkaTemplate;

    public void publishTripRequested(TripRequestedEvent event) {
        kafkaTemplate.send(TOPIC, event.tripId().toString(), event);
    }
}