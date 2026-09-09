package com.api.movie.controller;

import com.api.movie.entity.Movie;
import com.api.movie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovie(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.getMovieById(id));
    }

    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Movie>> searchMovie(@RequestParam String name) {
        return ResponseEntity.ok(movieService.searchMovie(name));
    }

    @PostMapping
    public ResponseEntity<Movie> saveMovie(@RequestBody Movie movie) {
        return ResponseEntity.ok(movieService.saveMovie(movie));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody Movie movie) {
        return ResponseEntity.ok(movieService.updateMovie(id, movie));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }
}
