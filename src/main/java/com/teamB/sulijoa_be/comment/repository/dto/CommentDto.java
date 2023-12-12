package com.teamB.sulijoa_be.comment.repository.dto;

import com.teamB.sulijoa_be.comment.repository.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentDto {
	private String userID;
	private String restaurantName;
	private String content;

	public Comment toEntity() {
		return Comment.builder()
						.userID(userID)
						.restaurantName(restaurantName)
						.content(content)
						.build();
	}
}
