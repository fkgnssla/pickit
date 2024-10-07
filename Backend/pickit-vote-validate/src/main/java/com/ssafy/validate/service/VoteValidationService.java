package com.ssafy.validate.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.kafka.shaded.com.google.protobuf.Api;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.ssafy.validate.dto.ApiResponse;
import com.ssafy.validate.dto.PickitLog;
import com.ssafy.validate.entity.VoteRecord;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VoteValidationService {

	private final MongoTemplate mongoTemplate;
	private final BlockchainLogService blockchainLogService;

	public List<VoteRecord> getStoredVotes(String contractAddress) {
		return mongoTemplate.findAll(VoteRecord.class, contractAddress);
	}

	public List<String> getContractAddresses() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		headers.set("Accept", "application/json");
		HttpEntity<String> entity = new HttpEntity<>(headers);

		ResponseEntity<ApiResponse> response = restTemplate.exchange(
			"https://j11a309.p.ssafy.io/api/vote-session/contract-address",
			HttpMethod.GET,
			entity,
			ApiResponse.class
		);
		return response.getBody().data();
	}


	@Transactional
	public void verifyVotes(String contractAddress, List<PickitLog> logs) {
		List<VoteRecord> storedVotes = getStoredVotes(contractAddress);
		List<VoteRecord> blockchainVotes = logs.stream()
			.map(blockchainLogService::mapLogToVoteRecord)
			.toList();

		for (VoteRecord vote : blockchainVotes) {
			if (!storedVotes.contains(vote)) {
				mongoTemplate.save(vote, contractAddress);
			}
		}
	}
}
