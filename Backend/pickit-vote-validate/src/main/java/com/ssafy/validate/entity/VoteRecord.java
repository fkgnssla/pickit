package com.ssafy.validate.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "#{#contractAddress}")
public record VoteRecord(
	String transactionHash,
	Long memberId,
	Long candidateId
){}
