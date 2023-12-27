package com.teamB.sulijoa_be.comment.repository;

import com.teamB.sulijoa_be.comment.repository.entity.Comment;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

import com.teamB.sulijoa_be.restaurant.repository.entity.Restaurant;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment>  findByRestaurantSeq(Long restaurantSeq);
    List<Comment> findByUserID(String userID);
    Comment findByRestaurantSeqAndContent(Long restaurantSeq, String content);

}
