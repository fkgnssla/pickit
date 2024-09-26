package com.ssafy.pickit.domain.broadcast.dto;

import com.ssafy.pickit.domain.broadcast.domain.Broadcast;

public record BroadcastResponse(
	String id,
	String name,
	String imgUrl
) {
	public static BroadcastResponse from(Broadcast broadcast) {
		return new BroadcastResponse(broadcast.getId(), broadcast.getName(), broadcast.getImgUrl());
	}
}
