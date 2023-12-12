package com.teamB.sulijoa_be.restaurant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DataInitializer {

	@Autowired
	private CsvReaderService csvReaderService;

	@PostConstruct
	public void init() {
		// 어플리케이션 시작 시 CSV 데이터를 데이터베이스에 저장
		csvReaderService.saveDataFromCSV();
	}
}
