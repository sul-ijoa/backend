package com.teamB.sulijoa_be.restaurant.repository;

import com.teamB.sulijoa_be.restaurant.repository.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
	@Query("SELECT COUNT(b) FROM Bookmark b WHERE b.restaurant = :restaurant")
	int countBookmarksByRestaurant(@Param("restaurant") Restaurant restaurant);
}
