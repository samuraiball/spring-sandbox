package com.example.kafkasample;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MyKafkaListener {

	@KafkaListener(topics = "my-replicated-topic")
	public void processMessage(String content) {
		System.out.println("content = " + content);
	}
}
