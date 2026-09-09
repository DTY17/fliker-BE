package com.api.movie.service;

import com.api.movie.entity.Comment;
import com.api.movie.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public List<Comment> getCommentsByUser(Long userId) {
        return commentRepository.findByUserId(userId);
    }

    public List<Comment> getCommentsByMovie(Long movieId) {
        return commentRepository.findByMovieId(movieId);
    }

    public Comment saveComment(Comment comment) {
        comment.setDate(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    public Comment updateComment(Long id, Comment updated) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        comment.setComment(updated.getComment());
        comment.setDate(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
