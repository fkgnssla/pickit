package com.ssafy.pickit.domain.vote.application.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VoteService {
	private final MongoTemplate mongoTemplate;
	private final RabbitTemplate rabbitTemplate;

}

