package com.ssafy.pickit.domain.broadcast.application.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.ssafy.pickit.domain.broadcast.domain.Broadcast;

@Repository
public interface BroadcastRepository extends MongoRepository<Broadcast, String> {
	@Query("{ 'index': ?0 }")
	Broadcast findByIndex(Long index);
}
