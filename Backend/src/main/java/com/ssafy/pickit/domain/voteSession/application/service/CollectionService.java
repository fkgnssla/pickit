package com.ssafy.pickit.domain.voteSession.application.service;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CollectionService {
	private final MongoTemplate mongoTemplate;

	public void createCollection(String collectionName) {
		try {
			if (!mongoTemplate.collectionExists(collectionName)) {
				mongoTemplate.createCollection(collectionName);
				log.info(collectionName + " 컬렉션이 생성되었습니다.");
			} else {
				log.error(collectionName + " 컬렉션이 이미 존재합니다.");
			}
		} catch (Exception e) {
			log.error("오류 발생: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
