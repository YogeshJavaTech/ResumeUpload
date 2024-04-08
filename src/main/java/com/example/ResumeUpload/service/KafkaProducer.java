package com.example.ResumeUpload.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {

    private static final String TOPIC = "resume-topic";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void publishMessage(String fileName) {
        kafkaTemplate.send(TOPIC, fileName);
    }
}
