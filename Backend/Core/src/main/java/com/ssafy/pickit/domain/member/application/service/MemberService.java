package com.ssafy.pickit.domain.member.application.service;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.ssafy.pickit.domain.member.application.repository.MemberRepository;
import com.ssafy.pickit.domain.member.domain.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;

	public void create(Member member) {
		memberRepository.save(member);
	}

	public Member findById(Long id) {
		return memberRepository.findById(id)
			.orElseThrow(() -> new NoSuchElementException("존재하지 않는 사용자입니다."));
	}

	public Member findBySocialId(String socialId) {
		return memberRepository.findBySocialId(socialId);
	}

}
