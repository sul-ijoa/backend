package com.teamB.sulijoa_be.restaurant.service;

import com.teamB.sulijoa_be.restaurant.repository.RestaurantQueryRepository;
import com.teamB.sulijoa_be.restaurant.repository.RestaurantRepository;
import com.teamB.sulijoa_be.restaurant.repository.dto.RestaurantDto;
import com.teamB.sulijoa_be.restaurant.repository.entity.QRestaurant;
import com.querydsl.core.BooleanBuilder;
import com.teamB.sulijoa_be.restaurant.repository.entity.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantService {

	private final RestaurantRepository restaurantRepository;
	private final RestaurantQueryRepository restaurantQueryRepository;

	@Autowired
	public RestaurantService(RestaurantRepository restaurantRepository,
													 @Qualifier("restaurantQueryRepository") RestaurantQueryRepository restaurantQueryRepository) {
		this.restaurantRepository = restaurantRepository;
		this.restaurantQueryRepository = restaurantQueryRepository;
	}

	/**
	 * 모든 레스토랑을 가져오는 메서드
	 *
	 * @return 레스토랑 목록에 대한 응답 DTO 리스트
	 */
	public List<RestaurantDto> getAllRestaurants() {
		List<Restaurant> restaurants = restaurantRepository.findAll();
		return restaurants.stream()
						.map(RestaurantDto::new)
						.collect(Collectors.toList());
	}

	/**
	 * 조건에 따라 레스토랑을 가져오는 메서드
	 *
	 * @param category       카테고리
	 * @param underSojuPrice 소주 가격 기준 (미만)
	 * @param moreSojuPrice  소주 가격 기준 (이상)
	 * @param underBeerPrice 맥주 가격 기준 (미만)
	 * @param moreBeerPrice  맥주 가격 기준 (이상)
	 * @return 조건에 맞는 레스토랑 목록에 대한 응답 DTO 리스트
	 */
	public List<RestaurantDto> getRestaurantsByCondition(String category, Integer underSojuPrice,
																											 Integer moreSojuPrice, Integer underBeerPrice,
																											 Integer moreBeerPrice) {
		BooleanBuilder predicate = buildPredicate(category, underSojuPrice, moreSojuPrice, underBeerPrice, moreBeerPrice);
		List<Restaurant> restaurants = restaurantQueryRepository.findRestaurantsByCondition(predicate);
		return restaurants.stream()
						.map(RestaurantDto::new)
						.collect(Collectors.toList());
	}

	/**
	 * 특정 레스토랑에 대한 상세 정보를 가져오는 메서드
	 *
	 * @param restaurant_seq 레스토랑 일련번호
	 * @return 레스토랑 상세 정보에 대한 응답 DTO
	 */

	/**
	 * 프론트에서 /detail-info를 사용하지 않고
	 * /all, /info에서 모든 response를 저장해서 사용하므로 /detail-info 주석처리함
	 * public RestaurantDto getRestaurantDetail(Long restaurant_seq) {
	 * 		Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurant_seq);
	 *
	 * 		if (optionalRestaurant.isEmpty()) {
	 * 			throw new RuntimeException("restaurant_seq not found: " + restaurant_seq);
	 *        }
	 *
	 * 		Restaurant restaurant = optionalRestaurant.get();
	 * 		return new RestaurantDto(restaurant);* 	}
	 */


	/**
	 * 조건에 따라 QueryDSL Predicate를 생성하는 메서드
	 *
	 * @param category       카테고리
	 * @param underSojuPrice 소주 가격 기준 (미만)
	 * @param moreSojuPrice  소주 가격 기준 (이상)
	 * @param underBeerPrice 맥주 가격 기준 (미만)
	 * @param moreBeerPrice  맥주 가격 기준 (이상)
	 * @return QueryDSL Predicate
	 */
	private BooleanBuilder buildPredicate(String category, Integer underSojuPrice, Integer moreSojuPrice,
																				Integer underBeerPrice, Integer moreBeerPrice) {
		BooleanBuilder predicate = new BooleanBuilder();
		QRestaurant qRestaurant = QRestaurant.restaurant;

		if (category != null && !category.isEmpty()) {
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

		return predicate;
	}
}
