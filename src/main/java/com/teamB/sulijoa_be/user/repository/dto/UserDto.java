package com.teamB.sulijoa_be.user.repository.dto;

import com.teamB.sulijoa_be.user.repository.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
	private String userID;
	private String userPW;
	private String nickname;

	public User toEntity() {
		return User.builder()
				.userID(userID)
				.userPW(userPW)
				.nickname(nickname)
				.build();
	}
}