package com.ssafy.pickit.global.s3.dto;

import java.util.List;

public record S3Response(
	String thumbnail,
	List<String> profileImgs) {
}
