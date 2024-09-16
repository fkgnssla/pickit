package com.ssafy.pickit.domain.tempVoteSession.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.pickit.domain.tempVoteSession.application.service.TempVoteSessionService;
import com.ssafy.pickit.domain.tempVoteSession.dto.TempVoteSessionRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "TempVoteSessionController", description = "임시 투표 세션 API")
@RestController
@RequestMapping("/temp-vote-session")
@RequiredArgsConstructor
public class TempVoteSessionController {
	private final TempVoteSessionService tempVoteSessionService;

	@Operation(summary = "임시 투표 세션 생성", description = "임시 투표 세션을 생성합니다.")
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> create(@ModelAttribute TempVoteSessionRequest voteSessionRequest) {
		return ResponseEntity.ok(tempVoteSessionService.create(voteSessionRequest));
	}

	@Operation(summary = "임시 투표 세션 전체 조회", description = "모든 임시 투표 세션을 반환합니다.")
	@GetMapping
	public ResponseEntity<?> findAll() {
		return ResponseEntity.ok(tempVoteSessionService.findAll());
	}

	@Operation(summary = "임시 투표 세션 삭제", description = "특정 임시 투표 세션을 삭제합니다.")
	@DeleteMapping
	public ResponseEntity<?> delete(@RequestParam("id") String id) {
		boolean result = tempVoteSessionService.delete(id);
		if (result)
			return ResponseEntity.noContent().build();
		else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 id를 가진 TempVoteSession이 존재하지 않습니다.");
	}

}
