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
	private final TempVoteSessionRepository voteSessionRepository;
	private final TempVoteSessionRepository tempVoteSessionRepository;
	private final BroadcastService broadcastService;

	@Transactional
	public TempVoteSession create(TempVoteSessionRequest voteSessionRequest) {
		List<TempCandidate> candidates = mapToCandidates(voteSessionRequest);

		TempVoteSession voteSession = buildVoteSession(voteSessionRequest, candidates);

		return voteSessionRepository.save(voteSession);
	}

	public List<TempVoteSessionResponse> findAll() {
		List<TempVoteSession> tempVoteSessions = voteSessionRepository.findAll();

		return mapToTempVoteSessionResponse(tempVoteSessions);
	}

	public TempVoteSession findById(String id) {
		return tempVoteSessionRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("Invalid tempVoteSession id: " + id));
	}

	public boolean delete(String id) {
		if (voteSessionRepository.existsById(id)) { // 엔티티가 존재하는지 확인
			voteSessionRepository.deleteById(id);
			return true;  // 삭제 성공
		}
		return false;
	}

	private @NotNull List<TempVoteSessionResponse> mapToTempVoteSessionResponse(
		List<TempVoteSession> tempVoteSessions) {
		List<TempVoteSessionResponse> tempVoteSessionResponses = tempVoteSessions.stream()
			.map(tempVoteSession -> {
				Broadcast findBroadcast = broadcastService.findById(tempVoteSession.getBroadcastId());
				return TempVoteSessionResponse.builder()
					.id(tempVoteSession.getId())
					.broadcastName(findBroadcast.getName())
					.title(tempVoteSession.getTitle())
					.description(tempVoteSession.getDescription())
					.candidates(tempVoteSession.getCandidates())
					.startDate(tempVoteSession.getStartDate())
					.endDate(tempVoteSession.getEndDate())
					.build();
			}).toList();
		return tempVoteSessionResponses;
	}

	// 후보자 리스트 매핑 메서드로 분리
	private List<TempCandidate> mapToCandidates(TempVoteSessionRequest voteSessionRequest) {
		return voteSessionRequest.getCandidates()
			.stream()
			.map(request -> TempCandidate.builder()
				.name(request.getName())
				.imgUrl("sample_url") // 상수화된 이미지 URL
				.voteCnt(0L)
				.build())
			.toList();
	}

	// VoteSession 빌드 메서드로 분리
	private TempVoteSession buildVoteSession(TempVoteSessionRequest voteSessionRequest,
		List<TempCandidate> candidates) {
		return TempVoteSession.builder()
			.broadcastId(voteSessionRequest.getBroadcastId())
			.title(voteSessionRequest.getTitle())
			.description(voteSessionRequest.getDescription())
			.candidates(candidates)
			.startDate(voteSessionRequest.getStartDate())
			.endDate(voteSessionRequest.getEndDate())
			.build();
	}
}
