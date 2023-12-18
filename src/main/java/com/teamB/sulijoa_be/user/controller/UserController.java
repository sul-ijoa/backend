package com.teamB.sulijoa_be.user.controller;

import com.teamB.sulijoa_be.user.repository.dto.UserDto;
import com.teamB.sulijoa_be.user.repository.entity.User;
import com.teamB.sulijoa_be.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
	private final UserService userService;

	@PostMapping("/signup")
	public ResponseEntity<String> registerUser(@RequestBody UserDto userDto) {
		ResponseEntity<String> response = userService.registerUser(userDto);
		return response;
	}

	@PostMapping("/login")
	public ResponseEntity<String> loginUser(@RequestBody Map<String, String> request) {
		System.out.println("Login request received: " + request);

		String userID = request.get("userID");
		String userPW = request.get("userPW");

		User user = userService.loginUser(userID, userPW);

		if (user != null) {
			return ResponseEntity.ok("success");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error");
		}
	}

	@PostMapping("/checkNickname")
	public ResponseEntity<Map<String, Boolean>> checkNicknameAvailability(@RequestBody Map<String, String> requestBody) {
		String nickname = requestBody.get("nickname");
		return userService.checkNicknameAvailability(nickname);
	}

	@PostMapping("/checkUserId")
	public ResponseEntity<Map<String, Boolean>> checkUserIdAvailability(@RequestBody Map<String, String> requestBody) {
		String userID = requestBody.get("userID");
		return userService.checkUserIdAvailability(userID);
	}
}