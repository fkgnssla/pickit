package com.ssafy.pickit.domain.broadcast.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BroadcastResponse {
	private String id;
	private String name;
	private String imgUrl;
}
