package com.api.movie.repository;

import com.api.movie.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByUserId(Long userId);
    List<Comment> findByMovieId(Long movieId);
}