package com.ssafy.vote.application;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

	@KafkaListener(topics = "${KAFKA_TOPIC_NAME}", groupId = "${KAFKA_CONSUMER_GROUP_ID}")
	public void consume(String message) {
		System.out.println("Consumed message: " + message);
	}
}
