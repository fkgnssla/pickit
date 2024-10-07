package com.ssafy.pickit.domain.vote.application.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.pickit.domain.member.application.service.MemberService;
import com.ssafy.pickit.domain.member.domain.Member;
import com.ssafy.pickit.domain.vote.domain.Vote;
import com.ssafy.pickit.domain.vote.dto.VoteRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducerService {

	private final KafkaTemplate<String, String> kafkaTemplate;
	private final VoteService voteService;
	private final MemberService memberService;
	private final ObjectMapper objectMapper;

	@Value("${spring.kafka.topic.vote-topic}")
	private String VOTE_TOPIC;

	public void sendVoteRequest(Long id, VoteRequest voteRequest) {
		try {
			log.info("VoteRequest 객체 생성: " + voteRequest.toString());

			String voteRequestJson = objectMapper.writeValueAsString(
				new VoteRequest(id, voteRequest.voteSessionId(), voteRequest.candidateId(),
					voteRequest.transactionHash()));
			kafkaTemplate.send(VOTE_TOPIC, voteRequest.transactionHash(), voteRequestJson);
			log.info("투표 요청 전송 -> id : " + id + " | value: " + voteRequestJson);

			Member member = memberService.findById(id);
			voteService.create(Vote.of(voteRequest, member));
		} catch (JsonProcessingException e) {
			log.error("VoteRequest 직렬화 중 오류 발생: ", e);
		}
	}
}
