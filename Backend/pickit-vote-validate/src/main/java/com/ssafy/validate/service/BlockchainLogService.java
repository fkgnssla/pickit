package com.ssafy.validate.service;

import java.math.BigInteger;
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
		BigInteger serviceId = (BigInteger) pickitLog.returnValues().get("serviceId");
		BigInteger candidateId = (BigInteger) pickitLog.returnValues().get("candidateIndex");
		return new VoteRecord(
			pickitLog.transactionHash(),
			serviceId.longValue(), // voter 주소를 memberId로 변환
			candidateId.longValue() // candidateIndex
		);
	}
}
