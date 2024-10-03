package com.ssafy.vote.application;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
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
	private final ObjectMapper objectMapper;

	@RetryableTopic(attempts = "5", backoff = @Backoff(delay = 1000, multiplier = 2))
	@KafkaListener(topics = "${spring.kafka.topic.vote-topic}", groupId = "${spring.kafka.consumer.group-id}")
	public void listenVoteRequests(ConsumerRecord<String, String> record) {
		try {
			VoteRequest voteRequest = objectMapper.readValue(record.value(), VoteRequest.class);
			log.info("받은 메시지 키: " + record.key() + " 밸류 : " + record.value());
			voteSessionService.updateVoteCnt(voteRequest.voteSessionId(), voteRequest.candidateId());
			String collectionName = voteRequest.voteSessionId();
			mongoTemplate.save(VoteRecord.of(voteRequest), collectionName);
		} catch (Exception e) {
			log.error("메시지 처리 중 오류 발생: ", e);
		}
	}
}