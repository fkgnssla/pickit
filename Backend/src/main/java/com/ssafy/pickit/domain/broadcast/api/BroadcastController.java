package com.ssafy.pickit.domain.broadcast.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.pickit.domain.broadcast.application.service.BroadcastService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "BroadcastController", description = "방송사 정보 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/broadcast")
public class BroadcastController {
	private final BroadcastService broadcastService;

	@Operation(summary = "전체 방송사 정보 조회", description = "방송사 식별자, 이름, 이미지를 반환합니다.")
	@GetMapping
	public ResponseEntity<?> findAll() {
		return ResponseEntity.ok(broadcastService.findAll());
	}
}

