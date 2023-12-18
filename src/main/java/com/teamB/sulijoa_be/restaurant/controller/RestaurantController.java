package com.teamB.sulijoa_be.restaurant.controller;

import com.teamB.sulijoa_be.restaurant.repository.dto.RestaurantDto;
import com.teamB.sulijoa_be.restaurant.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/restaurant")
public class RestaurantController {

	private final RestaurantService restaurantService;

	@Autowired
	public RestaurantController(RestaurantService restaurantService) {
		this.restaurantService = restaurantService;
	}

	/**
	 * 모든 레스토랑 정보를 가져오는 메서드
	 *
	 * @return 레스토랑 목록에 대한 응답 DTO
	 */
	@GetMapping("/all")
	public List<RestaurantDto.AllInfoResponse> getAllRestaurants() {
		return restaurantService.getAllRestaurants();
	}

	/**
	 * 조건에 따라 레스토랑 정보를 가져오는 메서드
	 *
	 * @param category        카테고리
	 * @param underSojuPrice  소주 가격 기준 (미만)
	 * @param moreSojuPrice   소주 가격 기준 (이상)
	 * @param underBeerPrice  맥주 가격 기준 (미만)
	 * @param moreBeerPrice   맥주 가격 기준 (이상)
	 * @return 조건에 맞는 레스토랑 목록에 대한 응답 DTO
	 */
	@GetMapping("/info")
	public List<RestaurantDto.AllInfoResponse> getRestaurantsByCondition(
					@RequestParam(name = "category", required = false, defaultValue = "") String category,
					@RequestParam(name = "underSojuPrice", required = false) Integer underSojuPrice,
					@RequestParam(name = "moreSojuPrice", required = false) Integer moreSojuPrice,
					@RequestParam(name = "underBeerPrice", required = false) Integer underBeerPrice,
					@RequestParam(name = "moreBeerPrice", required = false) Integer moreBeerPrice) {
		return restaurantService.getRestaurantsByCondition(category, underSojuPrice, moreSojuPrice, underBeerPrice, moreBeerPrice);
	}

	/**
	 * 특정 레스토랑에 대한 상세 정보를 가져오는 메서드
	 *
	 * @param restaurant_seq 레스토랑 일련번호
	 * @return 레스토랑 상세 정보에 대한 응답 DTO
	 */
	@GetMapping("/detail-info")
	public RestaurantDto.DetailResponse getRestaurantDetail(@RequestParam(name = "restaurant_seq") Long restaurant_seq) {
		return restaurantService.getRestaurantDetail(restaurant_seq);
	}
}
