package com.teamB.sulijoa_be.restaurant.repository.dto;

import com.teamB.sulijoa_be.restaurant.repository.entity.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
}
