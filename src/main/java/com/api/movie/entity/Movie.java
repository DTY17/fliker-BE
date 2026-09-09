package com.api.movie.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "movies")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int year;
    private String country;
    private String genre;
    private String type;
    private String url;
    private String image;
}

