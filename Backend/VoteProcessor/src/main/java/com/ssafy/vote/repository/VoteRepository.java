package com.ssafy.vote.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.vote.domain.VoteEntity;

@Repository
public interface VoteRepository extends MongoRepository<VoteEntity, String> {
}