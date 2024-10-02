package com.ssafy.voteSession.application;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.voteSession.domain.VoteSession;
import com.ssafy.voteSession.repository.VoteSessionRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VoteSessionService {
	private final VoteSessionRepository voteSessionRepository;
	private final MongoTemplate mongoTemplate;

	private VoteSession findById(String id) {
		return voteSessionRepository.findById(id)
			.orElseThrow(() -> new NoSuchElementException("존재하지 않는 투표 정보입니다."));
	}

	public void updateVoteCnt(String voteSessionId, Long candidateId) {
		VoteSession voteSession = findById(voteSessionId);
		voteSession.updateVoteCnt(candidateId);
		voteSessionRepository.save(voteSession);
	}
}
