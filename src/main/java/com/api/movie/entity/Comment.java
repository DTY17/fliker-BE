package com.api.movie.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long movieId;
    private String comment;
    private LocalDateTime date;
}


