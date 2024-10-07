package com.ssafy.validate.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class BatchJobScheduler {

	private final JobLauncher jobLauncher;
	private final Job voteVerificationJob;

	@Scheduled(fixedRate = 3600000) // 1시간 간격으로 Job 실행
	public void runVoteVerificationJob() throws Exception {
		JobParameters params = new JobParametersBuilder()
			.addLong("time", System.currentTimeMillis())
			.toJobParameters();

		jobLauncher.run(voteVerificationJob, params);
	}
}
