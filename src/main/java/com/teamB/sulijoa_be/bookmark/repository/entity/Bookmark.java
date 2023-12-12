package com.teamB.sulijoa_be.bookmark.repository.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Table(name = "bookmark")
public class Bookmark {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bookmart_seq;

	@Column(name = "userID", nullable = false, length = 30)
	private String userID;

	@Column(name = "restaurantName", nullable = false)
	private String restaurantName;
}
