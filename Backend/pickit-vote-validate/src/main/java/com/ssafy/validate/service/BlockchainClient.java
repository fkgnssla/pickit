package com.ssafy.validate.service;

import org.springframework.beans.factory.annotation.Value;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.exceptions.ContractCallException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.ssafy.validate.dto.PickitLog;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;

@Service
@Slf4j
public class BlockchainClient {

	private Web3j web3j;

	@Value("${web3j.client-address}")
	private String networkUrl;

	@PostConstruct
	public void init() {
		OkHttpClient.Builder builder = new OkHttpClient.Builder()
			.connectTimeout(60, TimeUnit.SECONDS)  // 연결 타임아웃
			.readTimeout(60, TimeUnit.SECONDS)     // 읽기 타임아웃
			.writeTimeout(60, TimeUnit.SECONDS);   // 쓰기 타임아웃

		OkHttpClient okHttpClient = builder.build();
		this.web3j = Web3j.build(new HttpService(networkUrl, okHttpClient));
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
			log.error(e.getMessage());
			throw new ContractCallException("블록체인 로그를 가져오는 중 오류가 발생했습니다.", e);
		}
	}
}
