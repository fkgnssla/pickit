package com.ssafy.pickit.domain.tempVoteSession.application.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ssafy.pickit.domain.tempVoteSession.domain.TempVoteSession;

public interface TempVoteSessionRepository extends MongoRepository<TempVoteSession, String> {
}
