package com.teamB.sulijoa_be.user.service;

import com.teamB.sulijoa_be.bookmark.repository.BookmarkRepository;
import com.teamB.sulijoa_be.bookmark.repository.entity.Bookmark;
import com.teamB.sulijoa_be.restaurant.repository.entity.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MypageService {

	@Autowired
	private BookmarkRepository bookmarkRepository;

	// 사용자가 찜한 가게 리스트 가져오기
	public List<Restaurant> getMyBookmarks(String userID) {
		List<Bookmark> bookmarks = bookmarkRepository.findByUserUserID(userID);
		return bookmarks.stream()
						.map(Bookmark::getRestaurant)
						.collect(Collectors.toList());
	}
}