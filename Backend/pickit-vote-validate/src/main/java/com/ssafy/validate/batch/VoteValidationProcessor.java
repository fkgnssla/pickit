package com.ssafy.validate.batch;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.ItemProcessor;

import com.ssafy.validate.dto.PickitLog;
import com.ssafy.validate.entity.VoteRecord;
import com.ssafy.validate.service.BlockchainLogService;
import com.ssafy.validate.service.VoteValidationService;

public class VoteValidationProcessor implements ItemProcessor<List<String>, List<VoteRecord>> {

	private final VoteValidationService voteValidationService;
	private final BlockchainLogService blockchainLogService;

	public VoteValidationProcessor(VoteValidationService voteValidationService, BlockchainLogService blockchainLogService) {
		this.voteValidationService = voteValidationService;
		this.blockchainLogService = blockchainLogService;
	}

	@Override
	public List<VoteRecord> process(List<String> contractAddresses) throws Exception {
		List<VoteRecord> invalidRecords = new ArrayList<>();
		for (String address : contractAddresses) {
			List<VoteRecord> voteRecords = voteValidationService.getStoredVotes(address);
			List<VoteRecord> blockchainLogs = blockchainLogService
				.getLogs(address)
				.stream()
				.map(blockchainLogService::mapLogToVoteRecord)
				.toList();

			for (VoteRecord voteRecord : voteRecords) {
				if (!blockchainLogs.contains(voteRecord)) {
					invalidRecords.add(voteRecord);
				}
			}
		}
		return invalidRecords;
	}
}

