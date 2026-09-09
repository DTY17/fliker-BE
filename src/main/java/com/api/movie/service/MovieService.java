package com.api.movie.service;

import com.api.movie.entity.Movie;
import com.api.movie.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    public Movie getMovieById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public List<Movie> searchMovie(String name) {
        return movieRepository.findByNameContainingIgnoreCase(name);
    }

    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public Movie updateMovie(Long id, Movie updated) {
        Movie movie = getMovieById(id);
        movie.setName(updated.getName());
        movie.setYear(updated.getYear());
        movie.setCountry(updated.getCountry());
        movie.setGenre(updated.getGenre());
        movie.setType(updated.getType());
        movie.setUrl(updated.getUrl());
        return movieRepository.save(movie);
    }

    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }
}

