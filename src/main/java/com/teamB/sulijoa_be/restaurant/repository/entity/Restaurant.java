package com.teamB.sulijoa_be.restaurant.repository.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Table(name = "restaurant")
public class Restaurant {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long restaurant_seq;

	@Column(nullable = false)
	private String address;

	@Column(nullable = false)
	private String restaurantName;

	@Column(nullable = false)
	private String category;

	@Column(nullable = false)
	private int sojuPrice;

	@Column(nullable = false)
	private int beerPrice;

	@Column(nullable = false)
	private String businessHours;

	@Column(nullable = false)
	private double lat;

	@Column(nullable = false)
	private double lon;

	@Column(nullable = false, length = 4096)
	private List<String> imageURLs;

	@Column
	@Builder.Default
	private int bookmarkCount = 0;

	public static RestaurantBuilder builder() {
		return new RestaurantBuilder();
	}
}
