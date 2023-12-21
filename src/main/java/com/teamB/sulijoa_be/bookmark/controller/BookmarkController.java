package com.teamB.sulijoa_be.bookmark.controller;

import com.teamB.sulijoa_be.bookmark.repository.dto.BookmarkDto;
import com.teamB.sulijoa_be.bookmark.service.BookmarkService;
import com.teamB.sulijoa_be.restaurant.repository.dto.RestaurantDto;
import com.teamB.sulijoa_be.restaurant.repository.entity.Restaurant;
import com.teamB.sulijoa_be.user.service.MypageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookmarks")
public class BookmarkController {

	private final BookmarkService bookmarkService;
	private final MypageService mypageService;

	@Autowired
	public BookmarkController(BookmarkService bookmarkService, MypageService mypageService) {
		this.bookmarkService = bookmarkService;
		this.mypageService = mypageService;
	}

	// 마이페이지에서 찜한 가게 리스트 가져오기
	@GetMapping
	public ResponseEntity<List<RestaurantDto>> getBookmarksByUserId(@RequestParam("userID") String userID) {
		List<Restaurant> bookmarkedRestaurants = mypageService.getMyBookmarks(userID);

		List<RestaurantDto> responseList = bookmarkedRestaurants.stream()
						.map(restaurant -> new RestaurantDto(restaurant))
						.collect(Collectors.toList());

		return ResponseEntity.ok(responseList);
	}

	@PostMapping("/add")
	public ResponseEntity<BookmarkDto.Response> addBookmark(@RequestBody BookmarkDto request) {
		String userID = request.getUserID();
		Long restaurant_seq = request.getRestaurant_seq();

		// 찜 추가 로직 호출
		bookmarkService.addBookmark(userID, restaurant_seq);

		// 가게 당 찜 개수 조회
		int restaurantBookmarkCount = bookmarkService.getRestaurantBookmarkCount(restaurant_seq);

		// 응답용 DTO 생성
		BookmarkDto.Response responseDto = new BookmarkDto.Response(restaurantBookmarkCount);

		// ResponseEntity에 응답 DTO를 담아 반환
		return ResponseEntity.ok(responseDto);
	}

	@PostMapping("/delete")
	public ResponseEntity<BookmarkDto.Response> removeBookmark(@RequestBody BookmarkDto request) {
		String userID = request.getUserID();
		Long restaurant_seq = request.getRestaurant_seq();

		// 찜 삭제 로직 호출
		bookmarkService.removeBookmark(userID, restaurant_seq);

		// 가게 당 찜 개수 조회
		int restaurantBookmarkCount = bookmarkService.getRestaurantBookmarkCount(restaurant_seq);

		// 응답용 DTO 생성
		BookmarkDto.Response responseDto = new BookmarkDto.Response(restaurantBookmarkCount);

		// ResponseEntity에 응답 DTO를 담아 반환
		return ResponseEntity.ok(responseDto);
	}
}