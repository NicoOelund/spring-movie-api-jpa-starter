package ek.osnb.starter.controller;

import ek.osnb.starter.DTO.CreateMovieRequest;
import ek.osnb.starter.DTO.MovieResponse;
import ek.osnb.starter.model.Movie;
import ek.osnb.starter.model.MovieDetails;
import ek.osnb.starter.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    public ResponseEntity<MovieResponse> createMovie(@RequestBody CreateMovieRequest request) {
        return ResponseEntity.ok(movieService.createMovie(request));
    }

    @GetMapping
    public ResponseEntity<List<MovieResponse>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> getMovieById(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.getMovieById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{movieId}/actors/{actorId}")
    public ResponseEntity<Movie> addActorToMovie(
            @PathVariable Long movieId,
            @PathVariable Long actorId) {
        // TODO: Call the service method
        Movie movie = movieService.addActorToMovie(movieId, actorId);
        // TODO: Return the updated movie
        return ResponseEntity.ok(movie);
    }

    @PostMapping("/{id}/details")
    public ResponseEntity<Movie> addDetailsToMovie(
            @PathVariable Long id,
            @RequestBody MovieDetails details) {
        // TODO: Call the service method
        Movie updatedMovie = movieService.addDetailsToMovie(id, details);
        // TODO: Return the updated movie
        return ResponseEntity.ok(updatedMovie);
    }
}
