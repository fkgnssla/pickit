package com.ssafy.vote.application;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.ssafy.vote.domain.VoteEntity;
import com.ssafy.vote.dto.VoteRequest;
import com.ssafy.vote.repository.VoteRepository;
import com.ssafy.voteSession.application.VoteSessionService;
import com.ssafy.voteSession.domain.VoteSession;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

	private final MongoTemplate mongoTemplate;
	private final VoteSessionService voteSessionService;

	@KafkaListener(topics = "${spring.kafka.topic.vote-topic}", groupId = "${spring.kafka.consumer.group-id}")

	public void listenVoteRequests(ConsumerRecord<Long, VoteRequest> record) {
		VoteRequest voteRequest = record.value();
		voteSessionService.updateVoteCnt(voteRequest.voteSessionId(), voteRequest.candidateId());

		String collectionName = voteRequest.voteSessionId();
		mongoTemplate.save(voteRequest, collectionName);
	}
}