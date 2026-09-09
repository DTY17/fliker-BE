package com.api.movie.controller;

import com.api.movie.entity.Comment;
import com.api.movie.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Comment>> getCommentsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(commentService.getCommentsByUser(userId));
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<Comment>> getCommentsByMovie(@PathVariable Long movieId) {
        return ResponseEntity.ok(commentService.getCommentsByMovie(movieId));
    }

    @PostMapping
    public ResponseEntity<Comment> saveComment(@RequestBody Comment comment) {
        return ResponseEntity.ok(commentService.saveComment(comment));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id, @RequestBody Comment comment) {
        return ResponseEntity.ok(commentService.updateComment(id, comment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
