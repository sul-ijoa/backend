package com.teamB.sulijoa_be.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamB.sulijoa_be.user.repository.dto.UserDto;
import com.teamB.sulijoa_be.user.service.UserDetailsServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

	private final ObjectMapper objectMapper;
	private final UserDetailsServiceImpl userDetailsService;

	// 회원 가입
	@PostMapping("/signup")
	public ResponseEntity<Map<String, String>> registerUser(@RequestBody UserDto userDto) {
		return userDetailsService.registerUser(userDto);
	}

//	// 로그인
//	@PostMapping("/login")
//	public ResponseEntity<Map<String, String>> loginUser(@RequestBody Map<String, String> request, HttpServletResponse response) {
//		String userID = request.get("userID");
//		String userPW = request.get("userPW");
//
//		// 로그인 메소드 호출
//		ResponseEntity<Map<String, String>> loginResponse = userDetailsService.loginUser(userID, userPW);
//
//		return loginResponse;
//	}

	// 로그인
	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> loginUser(@RequestBody Map<String, String> request, HttpServletRequest req, HttpServletResponse response) {
		String userID = request.get("userID");
		String userPW = request.get("userPW");
		Cookie[] cookies = req.getCookies();

		ResponseEntity<Map<String, String>> logined = isLogined(req, cookies);
		if (logined != null) {
			return logined;
		}

		// 로그인 메소드 호출 및 세션 쿠키 반환
		ResponseEntity<Map<String, String>> loginResponse = userDetailsService.loginUser(userID, userPW, response);

		// 세션 쿠키를 클라이언트로 전달
		if (loginResponse.getBody() != null && "success".equals(loginResponse.getBody().get("status"))) {
			return ResponseEntity.ok().header("Set-Cookie", response.getHeader("Set-Cookie")).body(loginResponse.getBody());
		} else {
			return loginResponse;
		}
	}

	private static ResponseEntity<Map<String, String>> isLogined(HttpServletRequest req, Cookie[] cookies) {
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("JSESSIONID")) {
					return ResponseEntity.ok().header("Set-Cookie", req.getHeader("Set-Cookie")).body(Collections.singletonMap("status", "success"));
				}
			}
		}
		return null;
	}

	// 로그아웃
	@PostMapping("/logout")
	public ResponseEntity<Map<String, String>> logoutUser(HttpServletRequest req, HttpServletResponse response) {
		return userDetailsService.logout(req, response);
	}

	// 닉네임 중복 확인
	@PostMapping("/checkNickname")
	public ResponseEntity<Map<String, Boolean>> checkNicknameAvailability(@RequestBody Map<String, String> requestBody) {
		String nickname = requestBody.get("nickname");
		return userDetailsService.checkNicknameAvailability(nickname);
	}

	// 사용자 ID 중복 확인
	@PostMapping("/checkUserId")
	public ResponseEntity<Map<String, Boolean>> checkUserIdAvailability(@RequestBody Map<String, String> requestBody) {
		String userID = requestBody.get("userID");
		return userDetailsService.checkUserIdAvailability(userID);
	}
}
