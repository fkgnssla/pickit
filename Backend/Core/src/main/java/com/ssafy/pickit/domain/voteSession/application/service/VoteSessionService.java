package com.ssafy.pickit.domain.voteSession.application.service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;

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
	private final VoteSessionDeployService voteSessionDeployService;
	private final CollectionService collectionService;

	@Transactional
	public void create(String id) {
		TempVoteSession tempVoteSession = tempVoteSessionService.findById(id);
		CompletableFuture<String> completableFuture = voteSessionDeployService.deploy(tempVoteSession.getCandidates());

		completableFuture.thenApply(contractAddress -> {
			if (contractAddress != null) {
				VoteSession voteSession = VoteSession.of(tempVoteSession, contractAddress,
					mapToCandidates(tempVoteSession));
				tempVoteSessionService.delete(id);

				VoteSession newVoteSession = voteSessionRepository.save(voteSession);
				collectionService.createCollection(newVoteSession.getId());
				return null;
			} else {
				throw new RuntimeException("투표 네트워크 자동 배포에 실패했습니다.");
			}
		});
	}

	public List<VoteSessionListResponse> findAllByOngoing() {
		List<VoteSession> voteSessions = voteSessionRepository.findAllByOngoing(LocalDateTime.now());
		voteSessions.sort(Comparator.comparingLong(this::calculateTotalVoteCnt).reversed());
		return mapToVoteSessionListResponse(voteSessions);
	}

	public List<VoteSessionListResponse> findAllByEnd() {
		List<VoteSession> voteSessions = voteSessionRepository.findAllByEnd(LocalDateTime.now(),
			Sort.by(Sort.Direction.DESC, "endDate"));
		return mapToVoteSessionListResponse(voteSessions);
	}

	public List<VoteSessionListResponse> findAllByBroadcastIdAndOngoing(String broadcastId) {
		List<VoteSession> voteSessions = voteSessionRepository.findAllByBroadcastIdAndOngoing(broadcastId,
			LocalDateTime.now());
		voteSessions.sort(Comparator.comparingLong(this::calculateTotalVoteCnt).reversed());
		return mapToVoteSessionListResponse(voteSessions);
	}

	private VoteSession findById(String id) {
		return voteSessionRepository.findById(id)
			.orElseThrow(() -> new NoSuchElementException("존재하지 않는 투표 정보입니다."));
	}

	public VoteSessionResponse findOne(String id) {
		return VoteSessionResponse.from(findById(id));
	}

	public void updateVoteCnt(String voteSessionId, String candidateId) {
		VoteSession voteSession = findById(voteSessionId);
		voteSession.updateVoteCnt(candidateId);
		voteSessionRepository.save(voteSession);
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
			.map(VoteSessionResponse::from).toList();
	}

	private static List<VoteSessionListResponse> mapToVoteSessionListResponse(List<VoteSession> voteSessions) {
		return voteSessions.stream()
			.map(VoteSessionListResponse::from).toList();
	}

	// 후보자 리스트 매핑 메서드로 분리
	private List<Candidate> mapToCandidates(TempVoteSession tempVoteSession) {
		return tempVoteSession.getCandidates()
			.stream()
			.map(Candidate::of)
			.toList();
	}
}
