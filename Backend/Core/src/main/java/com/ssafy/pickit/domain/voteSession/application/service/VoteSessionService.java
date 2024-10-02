package com.ssafy.pickit.domain.voteSession.application.service;

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

import com.ssafy.pickit.domain.tempVoteSession.application.service.TempVoteSessionService;
import com.ssafy.pickit.domain.tempVoteSession.domain.TempVoteSession;
import com.ssafy.pickit.domain.vote.application.service.VoteService;
import com.ssafy.pickit.domain.vote.domain.Vote;
import com.ssafy.pickit.domain.vote.dto.VoteValid;
import com.ssafy.pickit.domain.voteSession.application.repository.VoteSessionRepository;
import com.ssafy.pickit.domain.voteSession.domain.Candidate;
import com.ssafy.pickit.domain.voteSession.domain.VoteSession;
import com.ssafy.pickit.domain.voteSession.dto.CandidateResponse;
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
	private final MongoTemplate mongoTemplate;
	private final VoteService voteService;

	@Transactional
	public void create(String id) {
		TempVoteSession tempVoteSession = tempVoteSessionService.findById(id);
		CompletableFuture<String> completableFuture = voteSessionDeployService.deploy(tempVoteSession.getStartDate(),
			tempVoteSession.getEndDate(),
			tempVoteSession.getCandidates());

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

	public List<VoteSessionListResponse> findAllByOngoing(Long memberId) {
		List<VoteSession> voteSessions = voteSessionRepository.findAllByOngoing(LocalDateTime.now());
		voteSessions.sort(Comparator.comparingLong(this::calculateTotalVoteCnt).reversed());

		List<Vote> votes = voteService.findByMemberId(memberId);

		return mapToVoteSessionListResponse(voteSessions, votes);
	}

	public List<VoteSessionListResponse> findAllByEnd() {
		List<VoteSession> voteSessions = voteSessionRepository.findAllByEnd(LocalDateTime.now(),
			Sort.by(Sort.Direction.DESC, "endDate"));
		return mapToVoteSessionListResponse(voteSessions, null);
	}

	public List<VoteSessionListResponse> findAllByBroadcastIdAndOngoing(Long memberId, String broadcastId) {
		List<VoteSession> voteSessions = voteSessionRepository.findAllByBroadcastIdAndOngoing(broadcastId,
			LocalDateTime.now());
		voteSessions.sort(Comparator.comparingLong(this::calculateTotalVoteCnt).reversed());
		List<Vote> votes = voteService.findByMemberId(memberId);

		return mapToVoteSessionListResponse(voteSessions, votes);
	}

	private VoteSession findById(String id) {
		return voteSessionRepository.findById(id)
			.orElseThrow(() -> new NoSuchElementException("존재하지 않는 투표 정보입니다."));
	}

	public VoteSessionResponse findOne(String voteSessionId) {
		return VoteSessionResponse.from(findById(voteSessionId));
	}

	public List<CandidateResponse> findResult(Long memberId, String voteSessionId) {
		VoteSession voteSession = findById(voteSessionId);
		List<Candidate> candidates = voteSession.getCandidates();
		Long candidateId = checkVotedCandidate(memberId, voteSessionId);

		return candidates.stream()
			.map(candidate -> {
				if (candidate.getNumber() == candidateId) {
					return CandidateResponse.of(candidate, true);
				} else
					return CandidateResponse.of(candidate, false);
			}).toList();
	}

	public Long checkVotedCandidate(Long memberId, String voteSessionId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("memberId").is(memberId));
		VoteValid voteValid = mongoTemplate.findOne(query, VoteValid.class, voteSessionId);
		return (voteValid != null) ? voteValid.candidateId() : null;
	}

	public void updateVoteCnt(String voteSessionId, Long candidateId) {
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

		return mapToVoteSessionListResponse(voteSessions, null);
	}

	private static List<VoteSessionResponse> mapToVoteSessionResponse(List<VoteSession> voteSessions) {
		return voteSessions.stream()
			.map(VoteSessionResponse::from).toList();
	}

	private static List<VoteSessionListResponse> mapToVoteSessionListResponse(List<VoteSession> voteSessions,
		List<Vote> votes) {
		if (votes == null) {
			return voteSessions.stream()
				.map(vs -> VoteSessionListResponse.from(vs, false))
				.toList();
		}

		Set<String> votedSessionIds = votes.stream()
			.map(Vote::getVoteSessionId)
			.collect(Collectors.toSet());

		return voteSessions.stream()
			.map(vs -> VoteSessionListResponse.from(vs, votedSessionIds.contains(vs.getId())))
			.toList();
	}

	// 후보자 리스트 매핑 메서드로 분리
	private List<Candidate> mapToCandidates(TempVoteSession tempVoteSession) {
		return tempVoteSession.getCandidates()
			.stream()
			.map(Candidate::of)
			.toList();
	}
}
