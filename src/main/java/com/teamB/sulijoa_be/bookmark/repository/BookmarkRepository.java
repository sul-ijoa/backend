package com.teamB.sulijoa_be.bookmark.repository;

import com.teamB.sulijoa_be.bookmark.repository.entity.Bookmark;
import com.teamB.sulijoa_be.restaurant.repository.entity.Restaurant;
import com.teamB.sulijoa_be.user.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
	List<Bookmark> findByUserUserID(String userID);

	void deleteByUserAndRestaurant(User user, Restaurant restaurant);

	boolean existsByUserAndRestaurant(User user, Restaurant restaurant);

	@Query("SELECT COUNT(b) FROM Bookmark b WHERE b.restaurant = :restaurant")
	int countByRestaurant(@Param("restaurant") Restaurant restaurant);

}
