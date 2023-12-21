package com.teamB.sulijoa_be.bookmark.service;

import com.teamB.sulijoa_be.bookmark.repository.BookmarkRepository;
import com.teamB.sulijoa_be.bookmark.repository.entity.Bookmark;
import com.teamB.sulijoa_be.restaurant.repository.RestaurantRepository;
import com.teamB.sulijoa_be.restaurant.repository.entity.Restaurant;
import com.teamB.sulijoa_be.user.repository.UserRepository;
import com.teamB.sulijoa_be.user.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookmarkService {

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BookmarkRepository bookmarkRepository;

	// 찜 추가 메서드
	public void addBookmark(String userID, Long restaurant_seq) {
		User user = getUserById(userID);
		Restaurant restaurant = getRestaurantById(restaurant_seq);

		// 중복된 찜 방지
		if (!bookmarkRepository.existsByUserAndRestaurant(user, restaurant)) {
			Bookmark bookmark = Bookmark.builder()
							.user(user)
							.restaurant(restaurant)
							.build();

			bookmarkRepository.save(bookmark);

			// +1
			restaurant.setBookmarkCount(restaurant.getBookmarkCount() + 1);
			updateRestaurantBookmarkCount(restaurant);
		}
	}

	// 찜 삭제 메서드
	@Transactional
	public void removeBookmark(String userID, Long restaurant_seq) {
		User user = getUserById(userID);
		Restaurant restaurant = getRestaurantById(restaurant_seq);

		// 삭제
		bookmarkRepository.deleteByUserAndRestaurant(user, restaurant);

		// -1
		restaurant.setBookmarkCount(restaurant.getBookmarkCount() - 1);
		updateRestaurantBookmarkCount(restaurant);
	}

	// 가게의 찜 개수를 업데이트하는 메서드
	private void updateRestaurantBookmarkCount(Restaurant restaurant) {
		restaurantRepository.save(restaurant);
	}

	// 가게의 찜 개수 조회
	public int getRestaurantBookmarkCount(Long restaurant_seq) {
		Restaurant restaurant = getRestaurantById(restaurant_seq);
		return bookmarkRepository.countByRestaurant(restaurant);
	}

	// 사용자 ID로 사용자 엔티티를 가져오는 메서드
	private User getUserById(String userID) {
		return userRepository.findById(userID)
						.orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자 ID"));
	}

	// 가게 ID로 가게 엔티티를 가져오는 메서드
	private Restaurant getRestaurantById(Long restaurant_seq) {
		return restaurantRepository.findById(restaurant_seq)
						.orElseThrow(() -> new IllegalArgumentException("유효하지 않은 가게 ID"));
	}
}