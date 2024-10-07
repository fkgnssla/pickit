package com.ssafy.validate.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.validate.dto.PickitLog;
import com.ssafy.validate.entity.VoteRecord;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BlockchainLogService {

	private final BlockchainClient blockchainClient;

	public List<PickitLog> getLogs(String contractAddress) {
		return blockchainClient.getLogs(contractAddress);
	}

	public VoteRecord mapLogToVoteRecord(PickitLog pickitLog) {
		return new VoteRecord(
			pickitLog.transactionHash(),
			Long.parseLong(pickitLog.returnValues().get("serviceId").toString(), 16), // voter 주소를 memberId로 변환
			Long.parseLong(pickitLog.returnValues().get("candidateIndex").toString()) // candidateIndex
		);
	}
}
