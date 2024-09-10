package com.ssafy.pickit.domain.voteSession.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.pickit.domain.tempVoteSession.application.service.TempVoteSessionService;
import com.ssafy.pickit.domain.tempVoteSession.domain.TempVoteSession;
import com.ssafy.pickit.domain.voteSession.application.repository.VoteSessionRepository;
import com.ssafy.pickit.domain.voteSession.domain.Candidate;
import com.ssafy.pickit.domain.voteSession.domain.VoteSession;

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
			.winner(null)
			.candidates(candidates)
			.startDate(tempVoteSession.getStartDate())
			.endDate(tempVoteSession.getEndDate())
			.build();
	}
}
