package com.teamB.sulijoa_be.user.service;

import com.teamB.sulijoa_be.user.repository.UserRepository;
import com.teamB.sulijoa_be.user.repository.dto.UserDto;
import com.teamB.sulijoa_be.user.repository.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public User loginUser(String userID, String userPW) {
		User user = userRepository.findByUserID(userID);
		if (user != null && passwordEncoder.matches(userPW, user.getUserPW())) {
			return user;
		}
		return null;
	}

	public ResponseEntity<String> registerUser(UserDto userDto) {
		if (userRepository.existsByUserID(userDto.getUserID())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID already exists");
		}

		// 비밀번호 암호화
		String encodedPassword = passwordEncoder.encode(userDto.getUserPW());
		userDto.setUserPW(encodedPassword);

		userRepository.save(userDto.toEntity());
		return ResponseEntity.status(HttpStatus.CREATED).body("success");
	}

	public ResponseEntity<Map<String, Boolean>> checkNicknameAvailability(String nickname) {
		boolean isAvailable = !userRepository.existsByNickname(nickname);
		Map<String, Boolean> response = Collections.singletonMap("duplication", !isAvailable);
		return ResponseEntity.ok(response);
	}

	public ResponseEntity<Map<String, Boolean>> checkUserIdAvailability(String userID) {
		boolean isAvailable = !userRepository.existsByUserID(userID);
		Map<String, Boolean> response = Collections.singletonMap("duplication", !isAvailable);
		return ResponseEntity.ok(response);
	}
}