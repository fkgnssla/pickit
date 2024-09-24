package com.ssafy.pickit.domain.vote.application.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ssafy.pickit.domain.vote.dto.VoteRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class VoteProducer {
	private final RabbitTemplate rabbitTemplate;

	@Value("${rabbitmq.exchange.name}")
	private String EXCHANGE_NAME;

	@Value("${rabbitmq.routing.key}")
	private String ROUTING_KEY;

	public void submitVote(Long memberId, VoteRequest voteRequest) {
		VoteRequest newVoteRequest = voteRequest.withMemberId(memberId);
		log.info("==> [전송] : " + newVoteRequest.toString());
		rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, newVoteRequest);
	}
}
