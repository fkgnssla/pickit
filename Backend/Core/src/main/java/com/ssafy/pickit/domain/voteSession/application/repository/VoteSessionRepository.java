package com.ssafy.pickit.domain.voteSession.application.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ssafy.pickit.domain.voteSession.domain.VoteSession;

public interface VoteSessionRepository extends MongoRepository<VoteSession, String> {
	@Query("{ 'startDate': { $lte: ?0 }, 'endDate': { $gte: ?0 } }")
	List<VoteSession> findAllByOngoing(LocalDateTime currentTime);

	@Query("{ 'endDate': { $lte:  ?0} }")
	List<VoteSession> findAllByEnd(LocalDateTime currentTime, Sort sort);

	@Query("{ 'broadcastId': ?0, 'startDate': { $lte: ?1 }, 'endDate': { $gte: ?1 } }")
	List<VoteSession> findAllByBroadcastIdAndOngoing(Long broadcastId,
		LocalDateTime currentTime);

	@Query("{ 'broadcastId': ?0, 'endDate': { $lte:  ?1} }")
	List<VoteSession> findAllByBroadcastIdAndEnd(Long broadcastId, LocalDateTime currentTime, Sort sort);

	@Query("{ 'title': { $regex: ?0, $options: 'i' } }")
	List<VoteSession> findAllByTitle(String keyword);
}
