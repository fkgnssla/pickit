package com.ssafy.pickit.domain.vote.application.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.ssafy.pickit.domain.member.application.service.MemberService;
import com.ssafy.pickit.domain.vote.domain.Vote;
import com.ssafy.pickit.domain.vote.dto.VoteRequest;
import com.ssafy.pickit.domain.vote.dto.VoteValid;
import com.ssafy.pickit.domain.voteSession.application.service.VoteSessionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class VoteConsumer {
	private final MongoTemplate mongoTemplate;
	private final VoteSessionService voteSessionService;
	private final VoteService voteService;
	private final MemberService memberService;

	@RabbitListener(queues = "vote.queue")
	public void receiveVoteRequest(VoteRequest voteRequest) {
		log.info("<== [수신] : " + voteRequest.toString());
		voteSessionService.updateVoteCnt(voteRequest.voteSessionId(), voteRequest.candidateId());

		voteService.create(Vote.of(voteRequest, memberService.findById(voteRequest.memberId())));

		mongoTemplate.insert(VoteValid.of(voteRequest), voteRequest.voteSessionId());
	}
}
