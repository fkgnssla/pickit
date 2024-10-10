package com.ssafy.pickit.domain.vote.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.pickit.domain.vote.application.repository.VoteRepository;
import com.ssafy.pickit.domain.vote.domain.Vote;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VoteService {
	private final VoteRepository voteRepository;

	@Transactional
	public void create(Vote vote) {
		voteRepository.save(vote);
	}

	public List<Vote> findByMemberId(Long memberId) {
		return voteRepository.findByMemberId(memberId);
	}
}

