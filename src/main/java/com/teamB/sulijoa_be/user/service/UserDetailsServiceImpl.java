package com.teamB.sulijoa_be.user.service;

import com.teamB.sulijoa_be.user.repository.UserRepository;
import com.teamB.sulijoa_be.user.repository.dto.UserDto;
import com.teamB.sulijoa_be.user.repository.entity.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;


	// 사용자 로그인
	public ResponseEntity<Map<String, String>> loginUser(String userID, String userPW, HttpServletResponse response) {
		// 사용자 정보 조회
		User user = userRepository.findByUserID(userID);
		if (user != null && passwordEncoder.matches(userPW, user.getUserPW())) {
			// 세션 토큰 생성 또는 사용자 ID를 토큰으로 사용할 수 있음
			String sessionToken = generateSessionTokenOrUseUserId(user);

			// HTTP 응답에 세션 쿠키 설정`
			Cookie sessionCookie = new Cookie("JSESSIONID", sessionToken);
			sessionCookie.setHttpOnly(true);  // JavaScript에 의한 접근을 방지하기 위해 HttpOnly로 설정
			sessionCookie.setMaxAge(3600);  // 쿠키의 유효 시간 설정 (초 단위)
			sessionCookie.setPath("/");  // 쿠키의 적용 경로 설정
			response.addCookie(sessionCookie);

			Cookie userIDCookie = new Cookie("USERID", user.getUserID());
			userIDCookie.setHttpOnly(true);  // JavaScript에 의한 접근을 방지하기 위해 HttpOnly로 설정
			userIDCookie.setMaxAge(3600);  // 쿠키의 유효 시간 설정 (초 단위)
			userIDCookie.setPath("/");  // 쿠키의 적용 경로 설정
			response.addCookie(userIDCookie);

			Cookie userNicknameCookie = new Cookie("NICKNAME", user.getNickname());
			userNicknameCookie.setHttpOnly(true);  // JavaScript에 의한 접근을 방지하기 위해 HttpOnly로 설정
			userNicknameCookie.setMaxAge(3600);  // 쿠키의 유효 시간 설정 (초 단위)
			userNicknameCookie.setPath("/");  // 쿠키의 적용 경로 설정
			response.addCookie(userNicknameCookie);

			// 로그인 성공 응답
			Map<String, String> responseMap = Collections.singletonMap("status", "success");
			return ResponseEntity.ok(responseMap);
		} else {
			// 로그인 실패 응답 로그인실패햇을때 지워지게 아니면 로그아웃하깅
			Map<String, String> responseMap = Collections.singletonMap("status", "error");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
		}
	}

	// 로그아웃
	public ResponseEntity<Map<String, String>> logout(HttpServletRequest req, HttpServletResponse response) {
		Cookie[] cookies = req.getCookies();

		ResponseEntity<Map<String, String>> loggedOut = isNotLogined(cookies);
		if (loggedOut != null) {
			return loggedOut;
		}

		// 세션 무효화
		req.getSession().invalidate();

		// 쿠키 삭제
		removeCookies(response);

		Map<String, String> responseMap = Collections.singletonMap("status", "success");
		return ResponseEntity.ok(responseMap);
	}

	private ResponseEntity<Map<String, String>> isNotLogined(Cookie[] cookies) {
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("JSESSIONID")) {
					return null;
				}
			}
		}
		Map<String, String> responseMap = Collections.singletonMap("status", "error");
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseMap);
	}

	private void removeCookies(HttpServletResponse response) {
		Cookie sessionCookie = new Cookie("JSESSIONID", null);
		sessionCookie.setHttpOnly(true);
		sessionCookie.setMaxAge(0);
		sessionCookie.setPath("/");
		response.addCookie(sessionCookie);

		Cookie userIDCookie = new Cookie("USERID", null);
		userIDCookie.setHttpOnly(true);
		userIDCookie.setMaxAge(0);
		userIDCookie.setPath("/");
		response.addCookie(userIDCookie);

		Cookie userNicknameCookie = new Cookie("NICKNAME", null);
		userNicknameCookie.setHttpOnly(true);
		userNicknameCookie.setMaxAge(0);
		userNicknameCookie.setPath("/");
		response.addCookie(userNicknameCookie);
	}


//	// 사용자 로그인
//	public ResponseEntity<Map<String, String>> loginUser(String userID, String userPW) {
//		// 사용자 정보 조회
//		User user = userRepository.findByUserID(userID);
//		if (user != null && passwordEncoder.matches(userPW, user.getUserPW())) {
//			// 로그인 성공 응답
//			Map<String, String> responseMap = Collections.singletonMap("status", "success");
//			return ResponseEntity.ok(responseMap);
//		} else {
//			// 로그인 실패 응답
//			Map<String, String> responseMap = Collections.singletonMap("status", "error");
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
//		}
//	}

	// 사용자 회원 가입
	public ResponseEntity<Map<String, String>> registerUser(UserDto userDto) {
		// 이미 존재하는 사용자 ID인 경우 에러 응답
		if (userRepository.existsByUserID(userDto.getUserID())) {
			Map<String, String> errorResponse = Collections.singletonMap("status", "ID already exists");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

		// 비밀번호 암호화
		String encodedPassword = passwordEncoder.encode(userDto.getUserPW());
		userDto.setUserPW(encodedPassword);

		// 사용자 정보 저장
		userRepository.save(userDto.toEntity());

		Map<String, String> successResponse = Collections.singletonMap("status", "success");
		return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
	}

	// 닉네임 중복 확인
	public ResponseEntity<Map<String, Boolean>> checkNicknameAvailability(String nickname) {
		// 닉네임 중복 여부 확인
		boolean isAvailable = !userRepository.existsByNickname(nickname);
		Map<String, Boolean> response = Collections.singletonMap("duplication", !isAvailable);
		return ResponseEntity.ok(response);
	}

	// 사용자 ID 중복 확인
	public ResponseEntity<Map<String, Boolean>> checkUserIdAvailability(String userID) {
		// 사용자 ID 중복 여부 확인
		boolean isAvailable = !userRepository.existsByUserID(userID);
		Map<String, Boolean> response = Collections.singletonMap("duplication", !isAvailable);
		return ResponseEntity.ok(response);
	}

	// Spring Security에서 요구하는 메소드 구현
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 사용자 ID로 사용자 정보 조회
		User user = userRepository.findByUserID(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}

		// Spring Security의 UserDetails를 반환
		return new org.springframework.security.core.userdetails.User(
				user.getUserID(),
				user.getUserPW(),
				Collections.emptyList()
		);
	}

	private String generateSessionTokenOrUseUserId(User user) {
		return UUID.randomUUID().toString();
	}
}