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

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/temp-vote-session")
@RequiredArgsConstructor
public class TempVoteSessionController {
	private final TempVoteSessionService tempVoteSessionService;

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> create(@ModelAttribute TempVoteSessionRequest voteSessionRequest) {
		return ResponseEntity.ok(tempVoteSessionService.create(voteSessionRequest));
	}

	@GetMapping
	public ResponseEntity<?> findAll() {
		return ResponseEntity.ok(tempVoteSessionService.findAll());
	}

	@DeleteMapping
	public ResponseEntity<?> delete(@RequestParam("id") String id) {
		boolean result = tempVoteSessionService.delete(id);
		if (result)
			return ResponseEntity.noContent().build();
		else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 id를 가진 TempVoteSession이 존재하지 않습니다.");
	}

}
