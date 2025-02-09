package com.clover.habbittracker.domain.habit.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clover.habbittracker.domain.habit.dto.HabitRequest;
import com.clover.habbittracker.domain.habit.dto.HabitResponse;
import com.clover.habbittracker.domain.habit.dto.MyHabitResponse;
import com.clover.habbittracker.domain.habit.service.HabitService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("habits")
@RequiredArgsConstructor
public class HabitController {

	private final HabitService habitService;

	@GetMapping
	ResponseEntity<List<MyHabitResponse>> getMyHabitList(Authentication authentication,@RequestParam(required = false) String date) {
		Long memberId = (Long) authentication.getPrincipal();
		return new ResponseEntity<>(habitService.getMyList(memberId,date),HttpStatus.OK);
	}

	@PostMapping
	ResponseEntity<Long> createHabit(
		Authentication authentication,
		@RequestBody HabitRequest request) {

		Long memberId = (Long)authentication.getPrincipal();
		return new ResponseEntity<>(habitService.register(memberId, request), HttpStatus.OK);
	}

	@PutMapping("{habitId}")
	ResponseEntity<HabitResponse> updateHabit(@PathVariable Long habitId,@RequestBody HabitRequest request) {
		return new ResponseEntity<>(habitService.updateMyHabit(habitId,request),HttpStatus.OK);
	}

	@DeleteMapping("{habitId}")
	ResponseEntity<Void> deleteHabit(@PathVariable Long habitId) {
		habitService.deleteHabit(habitId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("{habitId}/check")
	ResponseEntity<Void> HabitCheck(@PathVariable Long habitId) {
		habitService.habitCheck(habitId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("{habitId}/check")
	ResponseEntity<Void> HabitUnCheck(@PathVariable Long habitId) {
		habitService.HabitUnCheck(habitId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
