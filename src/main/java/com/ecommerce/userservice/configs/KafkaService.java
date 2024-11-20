package com.ecommerce.userservice.configs;

import org.springframework.kafka.core.KafkaTemplate;

public class KafkaService {
    private KafkaTemplate<String, String> kafkaTemplate;

    public KafkaService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }
}
