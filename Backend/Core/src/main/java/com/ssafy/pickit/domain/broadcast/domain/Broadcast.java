package com.ssafy.pickit.domain.broadcast.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Document(collection = "broadcast")
@NoArgsConstructor
@Getter
public class Broadcast {
	private String id;
	private String name;
	private String imgUrl;
}
