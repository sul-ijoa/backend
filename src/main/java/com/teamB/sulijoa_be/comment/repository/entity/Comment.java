	package com.teamB.sulijoa_be.comment.repository.entity;

	import jakarta.persistence.*;
	import lombok.*;

	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@AllArgsConstructor
	@Builder
	@Entity
	@Getter
	@Setter
	@Table(name = "comment")
	public class Comment {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long comment_seq;

		@Column(name = "userID", length = 30, nullable = false)
		private String userID;

		@Column(name = "restaurant_seq", nullable = false)
		private Long restaurantSeq;

		@Column(name = "content", length = 300)
		private String content;

	}
