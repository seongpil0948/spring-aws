package com.sixplus.server.api.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kafka")
public class KafkaController {

    @Autowired
    private KafkaProducer kafkaProducer;
    @Autowired
    private DynamicKafkaProducer dynamicProducer;

    @PostMapping("/publish")
    public String publishMessage(@RequestParam("message") String message) {
        kafkaProducer.sendMessage(message);
        return "Message published successfully";
    }

    @PostMapping("/pub/dynamic")
    public String publishMessage(@RequestParam("topic") String topic, @RequestParam("message") String message) {
        dynamicProducer.sendMessage(topic, message);
        return "Message published successfully to topic: " + topic;
    }
}
