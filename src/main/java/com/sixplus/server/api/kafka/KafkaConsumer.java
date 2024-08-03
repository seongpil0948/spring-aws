package com.sixplus.server.api.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = "my_topic", groupId = "test_group_id")
    public void consume(String message) {
        System.out.println("Consumed my_topic message: " + message);
    }
}
