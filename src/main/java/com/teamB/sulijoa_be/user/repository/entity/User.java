package com.teamB.sulijoa_be.user.repository.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Table(name = "user")
public class User {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Id
	@Column(name = "userID", nullable = false, length = 30, unique = true)
	private String userID;

	@Column(name = "userPW", nullable = false, length = 255)
	private String userPW;

	@Column(name = "nickname", nullable = false, length = 8)
	private String nickname;
}
