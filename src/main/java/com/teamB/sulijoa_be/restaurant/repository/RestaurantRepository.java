package com.teamB.sulijoa_be.restaurant.repository;

import com.teamB.sulijoa_be.restaurant.repository.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

}