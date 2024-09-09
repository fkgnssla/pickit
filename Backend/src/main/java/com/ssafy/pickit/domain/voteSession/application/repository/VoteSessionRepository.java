package com.ssafy.pickit.domain.voteSession.application.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ssafy.pickit.domain.voteSession.domain.VoteSession;

public interface VoteSessionRepository extends MongoRepository<VoteSession, String> {
}
