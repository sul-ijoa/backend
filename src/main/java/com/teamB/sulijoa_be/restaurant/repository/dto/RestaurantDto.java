package com.teamB.sulijoa_be.restaurant.repository.dto;

import com.teamB.sulijoa_be.restaurant.repository.entity.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class RestaurantDto {
	private String address;
	private String restaurantName;
	private String category;
	private int sojuPrice;
	private int beerPrice;
	private String businessHours;
	private double lat;
	private double lon;
	private String imageURLs;

	@Builder.Default
	private int bookmarkCount = 0;

	public Restaurant toEntity() {
		return Restaurant.builder()
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
