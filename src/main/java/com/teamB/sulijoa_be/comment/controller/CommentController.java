package com.teamB.sulijoa_be.comment.controller;

import com.teamB.sulijoa_be.comment.repository.dto.CommentDto;
import com.teamB.sulijoa_be.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<?> createComment(@RequestBody CommentDto commentDto) {
        commentService.createComment(commentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("success");
    }

    @GetMapping
    public ResponseEntity<List<CommentDto>> getCommentsByRestaurantSeq(@RequestParam("restaurant_seq") Long restaurantSeq) {
        List<CommentDto> comments = commentService.getCommentsByRestaurantSeq(restaurantSeq);
        return ResponseEntity.ok(comments);
    }

    @PutMapping("/edit")
    public ResponseEntity<?> editComment(@RequestBody CommentDto commentDto) {
        commentService.editComment(commentDto);
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }


}
