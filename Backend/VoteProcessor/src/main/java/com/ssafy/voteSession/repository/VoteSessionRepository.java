package com.ssafy.voteSession.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ssafy.voteSession.domain.VoteSession;

public interface VoteSessionRepository extends MongoRepository<VoteSession, String> {
}
