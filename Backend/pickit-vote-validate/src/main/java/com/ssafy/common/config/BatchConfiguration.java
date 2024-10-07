package com.ssafy.common.config;

import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.client.RestTemplate;

import com.ssafy.validate.dto.PickitLog;
import com.ssafy.validate.service.BlockchainLogService;
import com.ssafy.validate.service.VoteValidationService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class BatchConfiguration {

	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;
	private final BlockchainLogService blockchainLogService;
	private final VoteValidationService voteValidationService;

	// Step 구성
	@Bean
	public Step verifyVoteStep() {
		return new StepBuilder("verifyVoteStep", jobRepository)
			.tasklet((contribution, chunkContext) -> {
				List<String> contractAddresses = voteValidationService.getContractAddresses();
				for (String contractAddress : contractAddresses) {
					List<PickitLog> logs = blockchainLogService.getLogs(contractAddress);
					voteValidationService.verifyVotes(contractAddress, logs);
				}
				return RepeatStatus.FINISHED;
			}, transactionManager)
			.build();
	}

	@Bean
	public Job voteVerificationJob(Step verifyVoteStep) {
		return new JobBuilder("voteVerificationJob", jobRepository)
			.start(verifyVoteStep)
			.build();
	}
}

