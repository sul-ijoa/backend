package com.teamB.sulijoa_be.comment.service;

import com.teamB.sulijoa_be.comment.repository.CommentRepository;
import com.teamB.sulijoa_be.comment.repository.dto.CommentDto;
import com.teamB.sulijoa_be.comment.repository.entity.Comment;
import com.teamB.sulijoa_be.restaurant.repository.RestaurantRepository;
import com.teamB.sulijoa_be.restaurant.repository.entity.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;
import java.util.stream.Collectors;


@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final RestaurantRepository restaurantRepository;
    @Autowired
    public CommentService(CommentRepository commentRepository, RestaurantRepository restaurantRepository) {
        this.commentRepository = commentRepository;
        this.restaurantRepository = restaurantRepository;
    }


    public void createComment(CommentDto commentDto) {
        Comment comment = commentDto.toEntity();
        commentRepository.save(comment);
    }

    @GetMapping
    public List<CommentDto> getCommentsByRestaurantSeq(@RequestParam("restaurantSeq") Long restaurantSeq) {
        List<Comment> comments = commentRepository.findByRestaurantSeq(restaurantSeq);
        return comments.stream()
                .map(comment -> {
                    CommentDto commentDto = new CommentDto(comment);

                    // 음식점 정보 가져오기
                    Restaurant restaurant = restaurantRepository.findById(comment.getRestaurantSeq()).orElse(null);
                    if (restaurant != null) {
                        commentDto.setRestaurantName(restaurant.getRestaurantName());
                        commentDto.setBeerPrice(restaurant.getBeerPrice());
                        commentDto.setSojuPrice(restaurant.getSojuPrice());
                        commentDto.setCategory(restaurant.getCategory());
                    }

                    return commentDto;
                })
                .collect(Collectors.toList());
    }

    @GetMapping
    public List<CommentDto> getCommentsByUserID(@RequestParam("userID") String userID) {
        return commentRepository.findByUserID(userID).stream()
                .map(comment -> {
                    CommentDto commentDto = new CommentDto(comment);

                    // 음식점 정보 가져오기
                    Restaurant restaurant = restaurantRepository.findById(comment.getRestaurantSeq()).orElse(null);
                    if (restaurant != null) {
                        commentDto.setRestaurantName(restaurant.getRestaurantName());
                        commentDto.setBeerPrice(restaurant.getBeerPrice());
                        commentDto.setSojuPrice(restaurant.getSojuPrice());
                        commentDto.setCategory(restaurant.getCategory());
                    }

                    return commentDto;
                })
                .collect(Collectors.toList());
    }

    public void editComment(CommentDto commentDto) {
        List<Comment> comments = commentRepository.findByUserID(commentDto.getUserID());
        if (comments.isEmpty()) {
            throw new IllegalArgumentException("No comment found with userID: " + commentDto.getUserID());
        }

        Comment existingComment = comments.get(0);  // 리스트에서 첫 번째 Comment를 선택합니다. 여기서는 사용자가 하나의 댓글만 작성할 수 있다고 가정하였습니다.
        existingComment.setContent(commentDto.getContent());
        commentRepository.save(existingComment);
    }

    public void deleteComment(Long restaurantSeq, String content) {
        Comment comment = commentRepository.findByRestaurantSeqAndContent(restaurantSeq, content);

        if (comment != null) {
            commentRepository.delete(comment);
        } else {
            throw new IllegalArgumentException("해당하는 댓글이 없습니다.");
        }
    }


}
