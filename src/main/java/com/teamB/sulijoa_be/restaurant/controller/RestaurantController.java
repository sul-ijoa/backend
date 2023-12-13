package com.teamB.sulijoa_be.restaurant.controller;

import com.teamB.sulijoa_be.restaurant.repository.RestaurantRepository;
import com.teamB.sulijoa_be.restaurant.repository.dto.RestaurantDto;
import com.teamB.sulijoa_be.restaurant.repository.entity.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/restaurant")
public class RestaurantController {

	@Autowired
	private RestaurantRepository restaurantRepository;

	@GetMapping("/info")
	public List<RestaurantDto> getAllRestaurants() {
		List<Restaurant> restaurants = restaurantRepository.findAll();
		return restaurants.stream()
						.map(RestaurantDto::new)
						.collect(Collectors.toList());
	}
}
