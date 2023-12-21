package com.teamB.sulijoa_be.bookmark.repository.dto;

import com.teamB.sulijoa_be.bookmark.repository.entity.Bookmark;
import com.teamB.sulijoa_be.restaurant.repository.entity.Restaurant;
import com.teamB.sulijoa_be.user.repository.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookmarkDto {
	private String userID;
	private Long restaurant_seq;

	// 응답용 DTO를 위한 내부 클래스
	@Getter
	@Setter
	@NoArgsConstructor
	public static class Response {
		private int bookmarkCount;

		public Response(int bookmarkCount) {
			this.bookmarkCount = bookmarkCount;
		}
	}

	public Bookmark toEntity(User user, Restaurant restaurant) {
		return Bookmark.builder()
						.user(user)
						.restaurant(restaurant)
						.build();
	}
}
