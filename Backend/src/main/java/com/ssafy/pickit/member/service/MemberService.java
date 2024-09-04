package com.ssafy.pickit.member.service;

import org.springframework.stereotype.Service;

import com.ssafy.pickit.member.domain.Member;
import com.ssafy.pickit.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;

	public void create(Member member) {
		memberRepository.save(member);
	}

	public Member findById(Long id) {
		return memberRepository.findById(id).orElse(null);
	}

	public Member findBySocialId(String socialId) {
		return memberRepository.findBySocialId(socialId);
	}

}
