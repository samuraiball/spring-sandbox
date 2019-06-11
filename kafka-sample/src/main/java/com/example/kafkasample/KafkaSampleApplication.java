package com.example.kafkasample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class KafkaSampleApplication implements CommandLineRunner {

	@Autowired
	KafkaTemplate kafkaTemplate;

	public static void main(String[] args) {
		SpringApplication.run(KafkaSampleApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		kafkaTemplate.send("my-replicated-topic", new HelloDto("hello"));

	}
}
