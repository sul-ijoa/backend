package com.teamB.sulijoa_be.restaurant.controller;

import com.teamB.sulijoa_be.restaurant.repository.RestaurantRepository;
import com.teamB.sulijoa_be.restaurant.repository.dto.RestaurantDto;
import com.teamB.sulijoa_be.restaurant.repository.entity.QRestaurant;
import com.teamB.sulijoa_be.restaurant.repository.entity.Restaurant;
import com.teamB.sulijoa_be.restaurant.repository.RestaurantQueryRepository;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/restaurant")
public class RestaurantController {

	private final RestaurantRepository restaurantRepository;
	private final RestaurantQueryRepository restaurantQueryRepository;

	@Autowired
	public RestaurantController(RestaurantRepository restaurantRepository,
															@Qualifier("restaurantQueryRepository") RestaurantQueryRepository restaurantQueryRepository) {
		this.restaurantRepository = restaurantRepository;
		this.restaurantQueryRepository = restaurantQueryRepository;
	}

	@GetMapping("/all")
	public List<RestaurantDto> getAllRestaurants() {
		List<Restaurant> restaurants = restaurantRepository.findAll();
		return restaurants.stream()
						.map(RestaurantDto::new)
						.collect(Collectors.toList());
	}

	@GetMapping("/info")
	public List<RestaurantDto> getRestaurantsByCondition(
					@RequestParam(name = "category", required = false, defaultValue = "") String category,
					@RequestParam(name = "underSojuPrice", required = false) Integer underSojuPrice,
					@RequestParam(name = "moreSojuPrice", required = false) Integer moreSojuPrice,
					@RequestParam(name = "underBeerPrice", required = false) Integer underBeerPrice,
					@RequestParam(name = "moreBeerPrice", required = false) Integer moreBeerPrice) {

		// QueryDSL을 사용하기 위한 Predicate 생성
		BooleanBuilder predicate = new BooleanBuilder();
		QRestaurant qRestaurant = QRestaurant.restaurant;

		// 파라미터에 따라 동적으로 쿼리에 조건 추가
		if (category != null && !category.isEmpty()) {
			// 카테고리 값에 따라 실제 데이터베이스의 카테고리 값으로 변환
			switch (category) {
				case "korean":
					predicate.and(qRestaurant.category.eq("한식"));
					break;
				case "japanese":
					predicate.and(qRestaurant.category.eq("일식"));
					break;
				case "chinese":
					predicate.and(qRestaurant.category.eq("중식"));
					break;
				case "western":
					predicate.and(qRestaurant.category.eq("양식"));
					break;
				case "hop/chicken":
					predicate.and(qRestaurant.category.eq("호프/통닭"));
					break;
				case "bar":
					predicate.and(qRestaurant.category.eq("술집"));
					break;
				case "gambar":
					predicate.and(qRestaurant.category.eq("감성주점"));
					break;
				default:
					predicate.and(qRestaurant.category.eq(category));
					break;
			}
		}

		if (underSojuPrice != null) {
			predicate.and(qRestaurant.sojuPrice.lt(underSojuPrice));
		}

		if (moreSojuPrice != null) {
			predicate.and(qRestaurant.sojuPrice.goe(moreSojuPrice));
		}

		if (underBeerPrice != null) {
			predicate.and(qRestaurant.beerPrice.lt(underBeerPrice));
		}

		if (moreBeerPrice != null) {
			predicate.and(qRestaurant.beerPrice.goe(moreBeerPrice));
		}

		// 동적으로 생성된 조건에 맞는 레스토랑 목록 조회
		List<Restaurant> restaurants = restaurantQueryRepository.findRestaurantsByCondition(predicate);

		// DTO로 변환하여 반환
		return restaurants.stream()
						.map(RestaurantDto::new)
						.collect(Collectors.toList());
	}
}