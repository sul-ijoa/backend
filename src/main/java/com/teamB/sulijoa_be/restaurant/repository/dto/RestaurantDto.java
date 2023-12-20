package com.teamB.sulijoa_be.restaurant.repository.dto;

import com.teamB.sulijoa_be.restaurant.repository.entity.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class RestaurantDto {
	private Long restaurant_seq;
	private String address;
	private String restaurantName;
	private String category;
	private int sojuPrice;
	private int beerPrice;
	private String businessHours;
	private double lat;
	private double lon;
	private List<String> imageURLs;

	@Builder.Default
	private int bookmarkCount = 0;

	// 생성자: Restaurant 엔터티에서 DTO로의 매핑
	public RestaurantDto(Restaurant restaurant) {
		this.restaurant_seq = restaurant.getRestaurant_seq();
		this.address = restaurant.getAddress();
		this.restaurantName = restaurant.getRestaurantName();
		this.category = restaurant.getCategory();
		this.sojuPrice = restaurant.getSojuPrice();
		this.beerPrice = restaurant.getBeerPrice();
		this.businessHours = restaurant.getBusinessHours();
		this.lat = restaurant.getLat();
		this.lon = restaurant.getLon();
		this.imageURLs = restaurant.getImageURLs();
		this.bookmarkCount = restaurant.getBookmarkCount();
	}

	// DTO에서 엔터티로의 매핑
	public Restaurant toEntity() {
		return Restaurant.builder()
						.restaurant_seq(restaurant_seq)
						.address(address)
						.restaurantName(restaurantName)
						.category(category)
						.sojuPrice(sojuPrice)
						.beerPrice(beerPrice)
						.businessHours(businessHours)
						.lat(lat)
						.lon(lon)
						.imageURLs(imageURLs)
						.bookmarkCount(bookmarkCount)
						.build();
	}

	// Inner class: /detail 엔드포인트 응답을 위한 DTO
	@Getter
	@Setter
	public static class DetailResponse {
		private Long restaurant_seq;
		private String restaurantName;
		private int sojuPrice;
		private int beerPrice;
		private String address;
		private String businessHours;
		private String category;
		private double lat;
		private double lon;
		private List<String> imageURLs;
		private int bookmarkCount;

		// 생성자: Restaurant 엔터티에서 /detail 응답 DTO로의 매핑
		public DetailResponse(Restaurant restaurant) {
			this.restaurant_seq = restaurant.getRestaurant_seq();
			this.restaurantName = restaurant.getRestaurantName();
			this.sojuPrice = restaurant.getSojuPrice();
			this.beerPrice = restaurant.getBeerPrice();
			this.address = restaurant.getAddress();
			this.businessHours = restaurant.getBusinessHours();
			this.category = restaurant.getCategory();
			this.lat = restaurant.getLat();
			this.lon = restaurant.getLon();
			this.imageURLs = restaurant.getImageURLs();
			this.bookmarkCount = restaurant.getBookmarkCount();
		}
	}

	// Inner class: /all 및 /info 엔드포인트 응답을 위한 DTO
	@Getter
	@Setter
	public static class AllInfoResponse {
		private Long restaurant_seq;
		private String restaurantName;
		private int sojuPrice;
		private int beerPrice;
		private String address;
		private String businessHours;
		private String category;
		private List<String> imageURLs;
		private int bookmarkCount;

		// 생성자: Restaurant 엔터티에서 /all 및 /info 응답 DTO로의 매핑
		public AllInfoResponse(Restaurant restaurant) {
			this.restaurant_seq = restaurant.getRestaurant_seq();
			this.restaurantName = restaurant.getRestaurantName();
			this.sojuPrice = restaurant.getSojuPrice();
			this.beerPrice = restaurant.getBeerPrice();
			this.address = restaurant.getAddress();
			this.businessHours = restaurant.getBusinessHours();
			this.category = restaurant.getCategory();
			this.imageURLs = restaurant.getImageURLs();
			this.bookmarkCount = restaurant.getBookmarkCount();
		}

		// DTO 리스트로 변환하는 메서드
		public static List<AllInfoResponse> fromRestaurantList(List<Restaurant> restaurants) {
			return restaurants.stream()
							.map(AllInfoResponse::new)
							.collect(Collectors.toList());
		}
	}
}
