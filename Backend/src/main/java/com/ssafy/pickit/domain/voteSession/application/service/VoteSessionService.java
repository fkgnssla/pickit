package com.ssafy.pickit.domain.voteSession.application.service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.pickit.domain.tempVoteSession.application.service.TempVoteSessionService;
import com.ssafy.pickit.domain.tempVoteSession.domain.TempVoteSession;
import com.ssafy.pickit.domain.voteSession.application.repository.VoteSessionRepository;
import com.ssafy.pickit.domain.voteSession.domain.Candidate;
import com.ssafy.pickit.domain.voteSession.domain.VoteSession;
import com.ssafy.pickit.domain.voteSession.dto.VoteSessionListResponse;
import com.ssafy.pickit.domain.voteSession.dto.VoteSessionResponse;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VoteSessionService {
	private final VoteSessionRepository voteSessionRepository;
	private final TempVoteSessionService tempVoteSessionService;

	@Transactional
	public VoteSession create(String id, String contractAddress) {
		TempVoteSession tempVoteSession = tempVoteSessionService.findById(id);
		VoteSession voteSession = buildVoteSession(tempVoteSession, contractAddress, mapToCandidates(tempVoteSession));
		tempVoteSessionService.delete(id);

		return voteSessionRepository.save(voteSession);
	}

	public List<VoteSessionListResponse> findAllByBroadcastIdAndOngoing(String broadcastId) {
		List<VoteSession> voteSessions = voteSessionRepository.findAllByBroadcastIdAndOngoing(broadcastId,
			LocalDateTime.now());
		voteSessions.sort(Comparator.comparingLong(this::calculateTotalVoteCnt).reversed());
		return mapToVoteSessionListResponse(voteSessions);
	}

	// 각 VoteSession 내 candidates의 voteCnt 총합을 기준으로 정렬
	private long calculateTotalVoteCnt(VoteSession voteSession) {
		return voteSession.getCandidates().stream()
			.mapToLong(Candidate::getVoteCnt)
			.sum();
	}

	public List<VoteSessionListResponse> findAllByBroadcastIdAndEnd(String broadcastId) {
		List<VoteSession> voteSessions = voteSessionRepository.findAllByBroadcastIdAndEnd(broadcastId,
			LocalDateTime.now(), Sort.by(Sort.Direction.DESC, "endDate"));

		return mapToVoteSessionListResponse(voteSessions);
	}

	private static List<VoteSessionResponse> mapToVoteSessionResponse(List<VoteSession> voteSessions) {
		return voteSessions.stream()
			.map(voteSession -> {
				return VoteSessionResponse.builder()
					.id(voteSession.getId())
					.title(voteSession.getTitle())
					.description(voteSession.getDescription())
					.imgUrl(voteSession.getImgUrl())
					.winner(voteSession.getWinner())
					.candidates(voteSession.getCandidates())
					.startDate(voteSession.getStartDate())
					.endDate(voteSession.getEndDate())
					.build();
			}).toList();
	}

	private static List<VoteSessionListResponse> mapToVoteSessionListResponse(List<VoteSession> voteSessions) {
		return voteSessions.stream()
			.map(voteSession -> {
				return VoteSessionListResponse.builder()
					.id(voteSession.getId())
					.title(voteSession.getTitle())
					.imgUrl(voteSession.getImgUrl())
					.startDate(voteSession.getStartDate())
					.endDate(voteSession.getEndDate())
					.build();
			}).toList();
	}

	// 후보자 리스트 매핑 메서드로 분리
	private List<Candidate> mapToCandidates(TempVoteSession tempVoteSession) {
		return tempVoteSession.getCandidates()
			.stream()
			.map(request -> Candidate.builder()
				.name(request.getName())
				.imgUrl(request.getImgUrl()) // 상수화된 이미지 URL
				.voteCnt(0L)
				.build())
			.toList();
	}

	// VoteSession 빌드 메서드로 분리
	private VoteSession buildVoteSession(TempVoteSession tempVoteSession, String contractAddress,
		List<Candidate> candidates) {
		return VoteSession.builder()
			.broadcastId(tempVoteSession.getBroadcastId())
			.contractAddress(contractAddress)
			.title(tempVoteSession.getTitle())
			.description(tempVoteSession.getDescription())
			.imgUrl(tempVoteSession.getImgUrl())
			.winner(null)
			.candidates(candidates)
			.startDate(tempVoteSession.getStartDate())
			.endDate(tempVoteSession.getEndDate())
			.build();
	}
}
