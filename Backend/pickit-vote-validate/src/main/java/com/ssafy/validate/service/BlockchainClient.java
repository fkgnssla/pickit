package com.ssafy.validate.service;

import org.springframework.beans.factory.annotation.Value;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.exceptions.ContractCallException;
import org.springframework.stereotype.Service;

import java.util.List;

import com.ssafy.validate.dto.PickitLog;

@Service
public class BlockchainClient {

	private final Web3j web3j;

	@Value("${web3j.client-address}")
	private String networkUrl;

	public BlockchainClient() {
		this.web3j = Web3j.build(new HttpService(networkUrl));
	}

	public List<PickitLog> getLogs(String contractAddress) {
		try {
			// 블록 필터 설정 (예: 최근 블록부터 최신 블록까지)
			EthFilter filter = new EthFilter(
				DefaultBlockParameterName.EARLIEST,
				DefaultBlockParameterName.LATEST,
				contractAddress
			);

			// 필터를 통해 이벤트 로그 가져오기
			return web3j
				.ethGetLogs(filter)
				.send()
				.getLogs()
				.stream()
				.map(PickitLog::toLog).toList();
		} catch (Exception e) {
			throw new ContractCallException("블록체인 로그를 가져오는 중 오류가 발생했습니다.", e);
		}
	}
}
