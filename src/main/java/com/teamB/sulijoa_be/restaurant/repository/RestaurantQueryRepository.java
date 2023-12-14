package com.teamB.sulijoa_be.restaurant.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.teamB.sulijoa_be.restaurant.repository.entity.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier; // Add this import
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.teamB.sulijoa_be.restaurant.repository.entity.QRestaurant.restaurant;

@Repository
public class RestaurantQueryRepository {

	private final JPAQueryFactory queryFactory;

	@Autowired
	public RestaurantQueryRepository(@Qualifier("jpaQueryFactory") JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}

	public List<Restaurant> findRestaurantsByCondition(Predicate predicate) {
		return queryFactory
						.selectFrom(restaurant)
						.where(predicate)
						.fetch();
	}
}