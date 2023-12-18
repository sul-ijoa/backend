package com.teamB.sulijoa_be.restaurant.service;

import com.teamB.sulijoa_be.restaurant.repository.RestaurantRepository;
import com.teamB.sulijoa_be.restaurant.repository.dto.RestaurantDto;
import com.teamB.sulijoa_be.restaurant.repository.entity.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

@Service
public class CsvReaderService {
	private static final String CSV_FILE_PATH = "C:\\Users\\Ahn Yoon Ju\\OneDrive\\바탕 화면\\sul-ijoa\\sul-ijoa_be\\src\\main\\resources\\data\\서교동_latlon_imageUrl.csv";

	@Autowired
	private RestaurantRepository restaurantRepository;

	// CSV 파일에서 데이터를 읽어서 데이터베이스에 저장
	public void saveDataFromCSV() {
		try (CSVReader reader = new CSVReader(new FileReader(CSV_FILE_PATH))) {
			String[] nextLine;

			// 헤더 건너뛰기
			reader.readNext();

			while ((nextLine = reader.readNext()) != null) {
				List<String> imageURLs = Arrays.asList(nextLine[8].replaceAll("[\\[\\]\\s\"]", "").split(","));
				RestaurantDto restaurantDto = RestaurantDto.builder()
								.address(nextLine[0])
								.restaurantName(nextLine[1])
								.category(nextLine[2])
								.sojuPrice(Integer.parseInt(nextLine[3]))
								.beerPrice(Integer.parseInt(nextLine[4]))
								.businessHours(nextLine[5])
								.lat(Double.parseDouble(nextLine[6]))
								.lon(Double.parseDouble(nextLine[7]))
								.imageURLs(imageURLs)
								.build();

				Restaurant restaurant = restaurantDto.toEntity();
				restaurantRepository.save(restaurant);
			}
		} catch (IOException | CsvValidationException e) {
			e.printStackTrace();
		}

	}
}