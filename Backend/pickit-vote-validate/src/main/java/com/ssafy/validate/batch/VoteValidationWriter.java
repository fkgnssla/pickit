package com.ssafy.validate.batch;

import java.util.List;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

import com.ssafy.validate.entity.VoteRecord;

public class VoteValidationWriter implements ItemWriter<List<VoteRecord>> {

	@Override
	public void write(Chunk<? extends List<VoteRecord>> invalidRecordsList) throws Exception {
		for (List<VoteRecord> invalidRecords : invalidRecordsList) {
			if (!invalidRecords.isEmpty()) {
				// 처리 로직 추가 (예: 로그 기록, 알림 전송 등)
				System.out.println("Invalid vote records found: " + invalidRecords);
			}
		}
	}
}

