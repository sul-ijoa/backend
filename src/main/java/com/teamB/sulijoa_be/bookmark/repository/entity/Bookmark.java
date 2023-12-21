package com.teamB.sulijoa_be.bookmark.repository.entity;

import com.teamB.sulijoa_be.restaurant.repository.entity.Restaurant;
import com.teamB.sulijoa_be.user.repository.entity.User;
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
	private Long bookmark_seq;

	@ManyToOne
	@JoinColumn(name = "userID")
	private User user;

	@ManyToOne
	@JoinColumn(name = "restaurant_seq")
	private Restaurant restaurant;
}
