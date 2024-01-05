	package com.teamB.sulijoa_be.comment.repository.dto;

	import com.teamB.sulijoa_be.comment.repository.entity.Comment;
	import lombok.*;

	@Getter
	@Setter
	@NoArgsConstructor
	public class CommentDto {
		private Long comment_seq;
		private String userID;
		private Long restaurant_seq;
		private String content;
		private String restaurant_name;
		private int beerPrice;
		private int sojuPrice;
		private String category;


		public Comment toEntity() {
			return Comment.builder()
					.userID(userID)
					.restaurantSeq(restaurant_seq)
					.content(content)
					.comment_seq(comment_seq)


					.build();
		}
		public CommentDto(Comment comment) {
			this.comment_seq = comment.getComment_seq();
			this.restaurant_seq = comment.getRestaurantSeq();
			this.userID = comment.getUserID();
			this.content = comment.getContent();

		}
		  public void setRestaurantName(String restaurantName)
		  {
		        this.restaurant_name = restaurantName;
		  }
	}