package com.ssafy.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
public class MongoConfig {

	@Value("${spring.data.mongodb.uri}")
	private String mongoUrl;

	@Value("${spring.data.mongodb.database}")
	private String mongoDatabase;

	@Bean
	public MongoClient mongoClient() {
		return MongoClients.create(mongoUrl);
	}

	@Bean
	public MongoTemplate mongoTemplate() {
		return new MongoTemplate(mongoClient(), mongoDatabase);
	}
}
