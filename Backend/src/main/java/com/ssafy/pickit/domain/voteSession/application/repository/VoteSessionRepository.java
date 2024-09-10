package com.ssafy.pickit.domain.voteSession.application.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ssafy.pickit.domain.voteSession.domain.VoteSession;

public interface VoteSessionRepository extends MongoRepository<VoteSession, String> {
	@Query("{ 'broadcastId': ?0, 'startDate': { $lte: ?1 }, 'endDate': { $gte: ?1 } }")
	List<VoteSession> findAllByBroadcastIdAndOngoing(String broadcastId,
		LocalDateTime currentTime);

	@Query("{ 'broadcastId': ?0, 'endDate': { $lte:  ?1} }")
	List<VoteSession> findAllByBroadcastIdAndEnd(String broadcastId, LocalDateTime currentTime, Sort sort);

}
