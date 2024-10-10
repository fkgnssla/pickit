package com.ssafy.validate.batch;

import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.ssafy.validate.service.BlockchainClient;
import com.ssafy.validate.service.VoteValidationService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ContractAddressReader implements ItemReader<List<String>> {

	private final BlockchainClient blockchainClient;
	private final VoteValidationService voteValidationService;

	@Override
	public List<String> read() throws Exception {
		return voteValidationService.getContractAddresses();
	}
}

