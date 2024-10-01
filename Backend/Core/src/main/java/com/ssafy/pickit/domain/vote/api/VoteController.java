package com.ssafy.pickit.domain.vote.api;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.pickit.domain.member.domain.PrincipalDetail;
import com.ssafy.pickit.domain.vote.application.service.KafkaProducerService;
import com.ssafy.pickit.domain.vote.application.service.VoteService;
import com.ssafy.pickit.domain.vote.dto.VoteRequest;
import com.ssafy.pickit.global.aop.LogExecution;
import com.ssafy.pickit.global.response.ApiResponse;
import com.ssafy.pickit.global.response.ResponseUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vote")
@LogExecution
public class VoteController {

	private final KafkaProducerService kafkaProducerService;
	private final VoteService voteService;

	@PostMapping
	public ApiResponse<?> sendVoteRequest(@RequestBody VoteRequest voteRequest,
		@AuthenticationPrincipal PrincipalDetail principalDetail) {
		kafkaProducerService.sendVoteRequest(principalDetail.getId(), voteRequest);
		return ResponseUtils.success();
	}
}
