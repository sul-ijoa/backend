package com.teamB.sulijoa_be.comment.service;

import com.teamB.sulijoa_be.comment.repository.CommentRepository;
import com.teamB.sulijoa_be.comment.repository.dto.CommentDto;
import com.teamB.sulijoa_be.comment.repository.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;


@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public void createComment(CommentDto commentDto) {
        Comment comment = commentDto.toEntity();
        commentRepository.save(comment);
    }

    public List<CommentDto> getCommentsByRestaurantSeq(Long restaurant_seq) {
        List<Comment> comments = commentRepository. findByRestaurantSeq(restaurant_seq);
        return comments.stream()
                .map(CommentDto::new)
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


}
