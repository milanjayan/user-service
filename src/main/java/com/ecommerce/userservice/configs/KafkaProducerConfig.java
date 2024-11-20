package com.ecommerce.userservice.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class KafkaProducerConfig {

    @Bean
    public KafkaService getKafkaService(KafkaTemplate<String, String> kafkaTemplate) {
        return new KafkaService(kafkaTemplate);
    }
}
