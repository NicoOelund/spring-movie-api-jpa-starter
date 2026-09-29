package ek.osnb.starter.service;

import ek.osnb.starter.DTO.ActorResponse;
import ek.osnb.starter.DTO.CreateMovieRequest;
import ek.osnb.starter.DTO.MovieDetailsResponse;
import ek.osnb.starter.DTO.MovieResponse;
import ek.osnb.starter.exceptions.NotFoundException;
import ek.osnb.starter.model.Actor;
import ek.osnb.starter.model.Movie;
import ek.osnb.starter.model.MovieDetails;
import ek.osnb.starter.model.Rating;
import ek.osnb.starter.repository.ActorRepository;
import ek.osnb.starter.repository.MovieDetailsRepository;
import ek.osnb.starter.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;
    private final MovieDetailsRepository movieDetailsRepository;

    public MovieService(MovieRepository movieRepository, ActorRepository actorRepository, MovieDetailsRepository movieDetailsRepository) {
        this.movieRepository = movieRepository;
        this.actorRepository = actorRepository;
        this.movieDetailsRepository = movieDetailsRepository;
    }

    public MovieResponse createMovie(CreateMovieRequest request) {
        Movie movie = toMovieEntity(request);
        Movie saved = movieRepository.save(movie);
        return toMovieResponse(saved);
    }

    public List<MovieResponse> getAllMovies() {
        var movies =movieRepository.findAll();
        List<MovieResponse> responses = new ArrayList<>();
        for (Movie m : movies) {
            responses.add(toMovieResponse(m));
        }
        return responses;
    }

    public MovieResponse getMovieById(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        return toMovieResponse(movie);
    }

    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }

    public Movie addActorToMovie(Long movieId, Long actorId) {
        // TODO: Find the movie by ID (throw exception if not found)
        Optional<Movie> movie = movieRepository.findById(movieId);
        if (movie.isEmpty()) {
            throw new NotFoundException("Movie not found");
        }
        // TODO: Find the actor by ID (throw exception if not found)
        Optional<Actor> actor = actorRepository.findById(actorId);
        if (actor.isEmpty()) {
            throw new NotFoundException("Actor not found");
        }
        // TODO: Add actor to movie's actors list
        Movie newMovie = movie.get();
        Actor newActor = actor.get();

        newMovie.getActors().add(newActor);
        // TODO: Save and return the updated movie
        movieRepository.save(newMovie);
        return newMovie;
    }

    public Movie addDetailsToMovie(Long movieId, MovieDetails details) {
        // TODO: Find the movie by ID
        Optional<Movie> foundMovie = movieRepository.findById(movieId);
        if (foundMovie.isEmpty()) {
            throw new NotFoundException("Movie not found");
        }
        Movie movie = foundMovie.get();
        // TODO: Save the details first using MovieDetailsRepository
        // movieDetailsRepository.save(details);
        // no longer needed with CascadeType.PERSIST
        // TODO: Set the details on the movie
        movie.setMovieDetails(details);
        // TODO: Set the movie reference on details (for bidirectional consistency)
        details.setMovie(movie);
        // TODO: Save and return the updated movie
        movieRepository.save(movie);
        return movie;
    }

    private MovieDetailsResponse toMovieDetailsResponse(MovieDetails movieDetails) {
        return new MovieDetailsResponse(
                movieDetails.getPlot(),
                movieDetails.getBudget(),
                movieDetails.getRuntime(),
                movieDetails.getProductionCompany()
                );
    }

    private ActorResponse toActorResponse(Actor actor) {
        return new ActorResponse(
                actor.getId(),
                actor.getName(),
                actor.getBirthYear()
        );
    }

    private MovieResponse toMovieResponse(Movie movie) {
        // TODO: Map Movie entity to MovieResponse DTO
        List<Actor> actorList = movie.getActors();
        List<ActorResponse> actorResponses = new ArrayList<>();
        for (Actor a : actorList) {
            actorResponses.add(toActorResponse(a)); // converts to DTO and adds to list
        }

        return new MovieResponse(
                movie.getId(),
                movie.getTitle(),
                movie.getReleaseYear(),
                movie.getGenre(),
                movie.getRating().getScore(),
                movie.getRating().getVoteCount(),
                toMovieDetailsResponse(movie.getMovieDetails()),
                actorResponses);
    }

    private Movie toMovieEntity(CreateMovieRequest request) {
        // TODO: Create new Movie from CreateMovieRequest
        // Create Rating object from ratingScore and ratingVoteCount
        Rating rating = new Rating(request.ratingScore(), request.ratingVoteCount());
        return new Movie(
                request.title(),
                request.releaseYear(),
                request.genre(),
                rating);
    }
}