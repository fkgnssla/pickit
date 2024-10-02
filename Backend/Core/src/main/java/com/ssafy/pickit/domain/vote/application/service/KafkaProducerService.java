package com.ssafy.pickit.domain.vote.application.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.ssafy.pickit.domain.vote.dto.VoteRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducerService {

	private final KafkaTemplate<String, VoteRequest> kafkaTemplate;

	private final String VOTE_TOPIC = "${spring.kafka.topic.vote-topic}";

	public void sendVoteRequest(Long id, VoteRequest voteRequest) {
		if(voteRequest.memberId().equals(id)){
			kafkaTemplate.send(VOTE_TOPIC, voteRequest.transactionHash(), voteRequest);
			log.debug("투표 요청 전송 -> id : " + id + " | value: " + voteRequest);
		}
	}
}
