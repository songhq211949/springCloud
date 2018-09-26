package com.songhq.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.apache.kafka.clients.NetworkClient;
@SpringBootApplication
public class KafkaApplication {
	public static void main(String[] args) {
		SpringApplication.run(KafkaApplication.class, args);
	}
}
