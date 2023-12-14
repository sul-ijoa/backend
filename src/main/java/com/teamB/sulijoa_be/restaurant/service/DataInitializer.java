package com.teamB.sulijoa_be.restaurant.service;

import com.teamB.sulijoa_be.restaurant.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class DataInitializer {

	@Autowired
	private CsvReaderService csvReaderService;

	@Autowired
	private RestaurantRepository restaurantRepository;

	private static final int MAX_ROWS = 556;

	private static boolean initialized = false;

	@PostConstruct
	public void init() {
		// 어플리케이션이 시작될 때 한 번만 실행
		if (!initialized) {
			// 테이블의 행 수 조회
			long rowCount = restaurantRepository.count();

			// 행 수가 MAX_ROWS 미만인 경우에만 CSV 데이터를 데이터베이스에 저장
			if (rowCount < MAX_ROWS) {
				csvReaderService.saveDataFromCSV();
			}

			initialized = true;
		}
	}
}
