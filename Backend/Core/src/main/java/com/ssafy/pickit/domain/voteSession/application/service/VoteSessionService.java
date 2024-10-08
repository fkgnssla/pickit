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
import com.ssafy.pickit.domain.voteSession.dto.PopularVoteSessionResponse;
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
				collectionService.createCollection(newVoteSession.getContractAddress());
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

	public List<VoteSessionListResponse> findByOngoingAndMy(Long memberId) {
		List<VoteSessionListResponse> voteSessionListResponses = findAllByOngoing(memberId);
		List<VoteSessionListResponse> myVoteSessionListResponses = voteSessionListResponses.stream()
			.filter(voteSessionListResponse -> voteSessionListResponse.isVote())
			.toList();

		return myVoteSessionListResponses;
	}

	public List<VoteSessionListResponse> findAllByEnd(Long memberId) {
		List<VoteSession> voteSessions = voteSessionRepository.findAllByEnd(LocalDateTime.now(),
			Sort.by(Sort.Direction.DESC, "endDate"));

		List<Vote> votes = voteService.findByMemberId(memberId);

		return mapToVoteSessionListResponse(voteSessions, votes);
	}

	public List<VoteSessionListResponse> findByEndAndMy(Long memberId) {
		List<VoteSessionListResponse> voteSessionListResponses = findAllByEnd(memberId);
		List<VoteSessionListResponse> myVoteSessionListResponses = voteSessionListResponses.stream()
			.filter(voteSessionListResponse -> voteSessionListResponse.isVote())
			.toList();

		return myVoteSessionListResponses;
	}

	public List<VoteSessionListResponse> findAllByBroadcastIdAndOngoing(Long memberId, Long broadcastId) {
		List<VoteSession> voteSessions = voteSessionRepository.findAllByBroadcastIdAndOngoing(broadcastId,
			LocalDateTime.now());
		voteSessions.sort(Comparator.comparingLong(this::calculateTotalVoteCnt).reversed());
		List<Vote> votes = voteService.findByMemberId(memberId);

		return mapToVoteSessionListResponse(voteSessions, votes);
	}

	public List<VoteSessionListResponse> findByTitle(Long memberId, String keyword) {
		List<VoteSession> voteSessions = voteSessionRepository.findAllByTitle(keyword);
		List<Vote> votes = voteService.findByMemberId(memberId);

		return mapToVoteSessionListResponse(voteSessions, votes);
	}

	public List<String> findContractAddress() {
		List<VoteSession> voteSessions = voteSessionRepository.findAllByOngoing(LocalDateTime.now());
		return voteSessions.stream()
			.map(vs -> vs.getContractAddress())
			.toList();
	}

	private VoteSession findById(String id) {
		return voteSessionRepository.findById(id)
			.orElseThrow(() -> new NoSuchElementException("존재하지 않는 투표 정보입니다."));
	}

	public List<PopularVoteSessionResponse> findAllByPopular() {
		List<VoteSession> voteSessions = voteSessionRepository.findAllByOngoing(LocalDateTime.now());
		voteSessions.sort(Comparator.comparingLong(this::calculateTotalVoteCnt).reversed());

		// 최대 3개의 투표 세션을 반환
		return voteSessions.stream()
			.limit(3)  // 최대 3개의 요소만 선택
			.map(PopularVoteSessionResponse::from)  // VoteSession을 PopularVoteSessionResponse로 변환
			.collect(Collectors.toList());  // 리스트로 반환
	}

	public VoteSessionResponse findOne(String voteSessionId) {
		return VoteSessionResponse.from(findById(voteSessionId));
	}

	public List<CandidateResponse> findResult(Long memberId, String voteSessionId) {
		VoteSession voteSession = findById(voteSessionId);
		List<Candidate> candidates = voteSession.getCandidates();
		Long candidateId = checkVotedCandidate(memberId, voteSession.getContractAddress());

		return candidates.stream()
			.map(candidate -> {
				if (candidate.getNumber() == candidateId) {
					return CandidateResponse.of(candidate, true);
				} else
					return CandidateResponse.of(candidate, false);
			}).toList();
	}

	public Long checkVotedCandidate(Long memberId, String contractAddress) {
		Query query = new Query();
		query.addCriteria(Criteria.where("memberId").is(memberId));
		VoteValid voteValid = mongoTemplate.findOne(query, VoteValid.class, contractAddress);
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

	public List<VoteSessionListResponse> findAllByBroadcastIdAndEnd(Long memberId, Long broadcastId) {
		List<VoteSession> voteSessions = voteSessionRepository.findAllByBroadcastIdAndEnd(broadcastId,
			LocalDateTime.now(), Sort.by(Sort.Direction.DESC, "endDate"));
		List<Vote> votes = voteService.findByMemberId(memberId);

		return mapToVoteSessionListResponse(voteSessions, votes);
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
			.map(vs -> VoteSessionListResponse.from(vs, votedSessionIds.contains(vs.getContractAddress())))
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
