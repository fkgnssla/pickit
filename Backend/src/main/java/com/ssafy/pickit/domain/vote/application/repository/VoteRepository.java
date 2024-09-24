package com.ssafy.pickit.domain.vote.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.pickit.domain.vote.domain.Vote;

public interface VoteRepository extends JpaRepository<Vote, Long> {
}
