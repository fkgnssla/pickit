package com.ssafy.validate.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.ssafy.validate.dto.PickitLog;
import com.ssafy.validate.entity.VoteRecord;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VoteValidationService {

	private final MongoTemplate mongoTemplate;
	private final BlockchainLogService blockchainLogService;
	private final RestTemplate restTemplate = new RestTemplate();

	public List<VoteRecord> getStoredVotes(String contractAddress) {
		return mongoTemplate.findAll(VoteRecord.class, contractAddress);
	}

	public List<String> getContractAddresses() {
		ResponseEntity<List<String>> response = restTemplate.exchange(
			"http://j11a309.p.ssafy.io/api/vote-session/contract-addresses", // 실제 REST API 경로
			HttpMethod.GET,
			null,
			new ParameterizedTypeReference<List<String>>() {}
		);
		return response.getBody();
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
