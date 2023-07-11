package com.ronaldogoncalves.coopvote.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class Producer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String kafkaTopic;

    public Producer(KafkaTemplate<String, String> kafkaTemplate, @Value("${voting.result.kafka.topic}") String kafkaTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaTopic = kafkaTopic;
    }

    public void send(String message) {
        String key = UUID.randomUUID().toString();
        kafkaTemplate.send(kafkaTopic, key, message);
    }
}
