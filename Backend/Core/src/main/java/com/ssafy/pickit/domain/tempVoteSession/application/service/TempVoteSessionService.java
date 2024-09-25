package com.ssafy.pickit.domain.tempVoteSession.application.service;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.pickit.domain.broadcast.application.service.BroadcastService;
import com.ssafy.pickit.domain.broadcast.domain.Broadcast;
import com.ssafy.pickit.domain.tempVoteSession.application.repository.TempVoteSessionRepository;
import com.ssafy.pickit.domain.tempVoteSession.domain.TempCandidate;
import com.ssafy.pickit.domain.tempVoteSession.domain.TempVoteSession;
import com.ssafy.pickit.domain.tempVoteSession.dto.TempVoteSessionRequest;
import com.ssafy.pickit.domain.tempVoteSession.dto.TempVoteSessionResponse;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TempVoteSessionService {
	private final TempVoteSessionRepository tempVoteSessionRepository;
	private final BroadcastService broadcastService;

	@Transactional
	public TempVoteSession create(TempVoteSessionRequest voteSessionRequest) {
		List<TempCandidate> candidates = mapToCandidates(voteSessionRequest);

		TempVoteSession voteSession = TempVoteSession.of(voteSessionRequest, "https://aws.s3", candidates);

		return tempVoteSessionRepository.save(voteSession);
	}

	public List<TempVoteSessionResponse> findAll() {
		List<TempVoteSession> tempVoteSessions = tempVoteSessionRepository.findAll();
		return mapToTempVoteSessionResponse(tempVoteSessions);
	}

	public TempVoteSession findById(String id) {
		return tempVoteSessionRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("Invalid tempVoteSession id: " + id));
	}

	public boolean delete(String id) {
		return tempVoteSessionRepository.findById(id)
			.map(entity -> {
				tempVoteSessionRepository.deleteById(id);
				return true;
			})
			.orElse(false);
	}

	private @NotNull List<TempVoteSessionResponse> mapToTempVoteSessionResponse(
		List<TempVoteSession> tempVoteSessions) {
		return tempVoteSessions.stream()
			.map(tempVoteSession -> {
				Broadcast findBroadcast = broadcastService.findById(tempVoteSession.getBroadcastId());
				return TempVoteSessionResponse.of(tempVoteSession, findBroadcast);
			}).toList();
	}

	// 후보자 리스트 매핑 메서드로 분리
	private List<TempCandidate> mapToCandidates(TempVoteSessionRequest voteSessionRequest) {
		return voteSessionRequest.candidates()
			.stream()
			.map(TempCandidate::of)
			.toList();
	}
}
