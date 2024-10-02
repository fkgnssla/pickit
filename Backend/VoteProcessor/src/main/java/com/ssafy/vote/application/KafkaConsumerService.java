package com.ssafy.vote.application;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.ssafy.vote.dto.VoteRequest;
import com.ssafy.voteSession.application.VoteSessionService;
import com.ssafy.voteSession.dto.VoteRecord;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService {

	private final MongoTemplate mongoTemplate;
	private final VoteSessionService voteSessionService;

	@KafkaListener(topics = "${spring.kafka.topic.vote-topic}", groupId = "${spring.kafka.consumer.group-id}")
	public void listenVoteRequests(ConsumerRecord<String, VoteRequest> record) {
		VoteRequest voteRequest = record.value();
		voteSessionService.updateVoteCnt(voteRequest.voteSessionId(), voteRequest.candidateId());
		log.info("받음" + record.key());
		String collectionName = voteRequest.voteSessionId();
		mongoTemplate.save(VoteRecord.of(voteRequest), collectionName);
	}
}