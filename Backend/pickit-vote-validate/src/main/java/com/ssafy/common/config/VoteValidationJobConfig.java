package com.ssafy.common.config;

import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.ssafy.validate.batch.ContractAddressReader;
import com.ssafy.validate.batch.VoteValidationProcessor;
import com.ssafy.validate.batch.VoteValidationWriter;
import com.ssafy.validate.entity.VoteRecord;
import com.ssafy.validate.service.BlockchainClient;
import com.ssafy.validate.service.BlockchainLogService;
import com.ssafy.validate.service.VoteValidationService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class VoteValidationJobConfig {

	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;
	private final BlockchainLogService blockchainLogService;
	private final VoteValidationService voteValidationService;
	private final BlockchainClient blockchainClient;

	@Bean
	public Job voteValidationJob() {
		return new JobBuilder("voteValidationJob", jobRepository)
			.start(validateVotesStep())
			.build();
	}

	@Bean
	public Step validateVotesStep() {
		return new StepBuilder("validateVotesStep", jobRepository)
			.<List<String>, List<VoteRecord>>chunk(1, transactionManager)
			.reader(contractAddressReader())
			.processor(voteValidationProcessor())
			.writer(voteValidationWriter())
			.build();
	}

	@Bean
	public ItemReader<List<String>> contractAddressReader() {
		return new ContractAddressReader(blockchainClient, voteValidationService);
	}

	@Bean
	public ItemProcessor<List<String>, List<VoteRecord>> voteValidationProcessor() {
		return new VoteValidationProcessor(voteValidationService, blockchainLogService);
	}

	@Bean
	public ItemWriter<List<VoteRecord>> voteValidationWriter() {
		return new VoteValidationWriter();
	}
}

