package com.ssafy.pickit.global.s3.api;

import java.util.List;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.pickit.global.response.ApiResponse;
import com.ssafy.pickit.global.response.ResponseUtils;
import com.ssafy.pickit.global.s3.S3Service;
import com.ssafy.pickit.global.s3.dto.S3Request;
import com.ssafy.pickit.global.s3.dto.S3Response;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class S3Controller {
	private final S3Service s3Service;

	@PostMapping
	public ApiResponse<?> upload(@ModelAttribute S3Request s3Request) {
		String thumbnail = s3Service.uploadProfile(s3Request.thumbnail());
		List<String> profileImgs = s3Service.uploadProfiles(s3Request.profileImgs());

		return ResponseUtils.success(new S3Response(thumbnail, profileImgs));
	}
}
