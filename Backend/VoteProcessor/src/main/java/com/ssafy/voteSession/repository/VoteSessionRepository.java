package com.ssafy.voteSession.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.ssafy.voteSession.domain.VoteSession;

public interface VoteSessionRepository extends MongoRepository<VoteSession, String> {
	Optional<VoteSession> findByContractAddress(String contractId);
}
