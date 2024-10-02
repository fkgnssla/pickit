package com.ssafy.voteSession.application;

import java.util.NoSuchElementException;
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

	private VoteSession findById(String id) {
		return voteSessionRepository.findById(id)
			.orElseThrow(() -> new NoSuchElementException("존재하지 않는 투표 정보입니다."));
	}

	@Transactional
	public void updateVoteCnt(String voteSessionId, Long candidateId) {
		VoteSession voteSession = findById(voteSessionId);
		voteSession.updateVoteCnt(candidateId);
		voteSessionRepository.save(voteSession);
	}
}
