package com.librasmart.plume.matching.listener;

import com.librasmart.plume.matching.event.TripRequestedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TripRequestedEventListener {

    @KafkaListener(topics = "trip-requested")
    public void consume(TripRequestedEvent event) {
        System.out.println("Received trip request: " + event);
    }
}