package com.ssafy.vote.application;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.ssafy.vote.domain.VoteEntity;
import com.ssafy.vote.repository.VoteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

	private final VoteRepository voteRepository;

	@KafkaListener(topics = "${spring.kafka.topic.vote-topic}", groupId = "${spring.kafka.consumer.group-id}")

	public void listenVoteRequests(ConsumerRecord<String, String> record) {
		VoteEntity voteEntity = new VoteEntity(record.key(), record.value());
		voteRepository.save(voteEntity);
		System.out.println("Vote saved: " + voteEntity);
	}
}