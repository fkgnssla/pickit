package com.ssafy.pickit.domain.voteSession.application.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ssafy.pickit.domain.tempVoteSession.domain.TempCandidate;
import com.ssafy.pickit.domain.voteSession.dto.VoteSessionDeployResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@EnableAsync
@RequiredArgsConstructor
public class VoteSessionDeployService {

	private final RestTemplate restTemplate;

	@Value("${vote-session.deploy.url}")
	private String url;

	@Async
	public CompletableFuture<String> deploy(List<TempCandidate> candidates) {
		// 요청 헤더 설정
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		// 요청 바디 설정
		Map<String, Object> requestBody = new HashMap<>();
		String[] candidateNames = candidates.stream()
			.map(candidate -> candidate.getName()).toArray(String[]::new);
		requestBody.put("candidate_names", candidateNames);

		// HttpEntity 객체로 헤더와 바디 설정
		HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

		// POST 요청 실행 및 응답 받기
		ResponseEntity<VoteSessionDeployResponse> response = restTemplate.exchange(url, HttpMethod.POST, request,
			VoteSessionDeployResponse.class);

		VoteSessionDeployResponse voteDeployResponse = response.getBody();
		if (voteDeployResponse != null) {
			log.info(voteDeployResponse.toString());
			log.info("Vote session deploy success.");
			return CompletableFuture.completedFuture(voteDeployResponse.contractAddress());
		} else {
			return CompletableFuture.completedFuture(null);
		}
	}
}
