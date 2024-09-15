package com.ssafy.pickit.domain.vote.application.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.ssafy.pickit.domain.vote.domain.Vote;
import com.ssafy.pickit.domain.vote.dto.VoteRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class VoteConsumer {
	private final MongoTemplate mongoTemplate;

	@RabbitListener(queues = "vote.queue")
	public void receiveVoteRequest(VoteRequest voteRequest) {
		log.info("<== [수신] : " + voteRequest.toString());
		Vote vote = Vote.of(voteRequest);
		mongoTemplate.insert(vote, voteRequest.getVoteSessionId());
	}
}
