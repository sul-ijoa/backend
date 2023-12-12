package com.teamB.sulijoa_be.bookmark.repository.dto;

import com.teamB.sulijoa_be.bookmark.repository.entity.Bookmark;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookmarkDto {
	private String userID;
	private String restaurantName;

	public Bookmark toEntity() {
		return Bookmark.builder()
						.userID(userID)
						.restaurantName(restaurantName)
						.build();
	}
}
