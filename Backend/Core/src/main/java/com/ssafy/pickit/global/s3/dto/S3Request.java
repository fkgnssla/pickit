package com.ssafy.pickit.global.s3.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public record S3Request(
	MultipartFile thumbnail,
	List<MultipartFile> profileImgs) {
}
