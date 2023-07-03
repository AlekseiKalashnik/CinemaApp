package com.application.Cinema.service;

import com.application.Cinema.model.Movie;
import com.application.Cinema.repository.MovieRepository;
import com.application.Cinema.util.exception_handling.movieException.MovieNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@Slf4j
public class MovieService {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(Integer id) {
        log.info("method getMovieById in MovieService");
        Optional<Movie> movieOptional = movieRepository.findById(id);
        return movieOptional.orElseThrow(MovieNotFoundException::new);
    }

    @Transactional
    public void createMovie(Movie movie) {
        log.info("method addNewMovie in MovieService");
        Optional<Movie> existedMovie = movieRepository
                .findMovieByNameAndAndCreationDate(movie.getName(), movie.getCreationDate());
        if (existedMovie.isPresent()) {
            throw new IllegalArgumentException("movie has already existed");
        }        enrichMovie(movie);
        movieRepository.save(movie);
    }

    @Transactional
    public void updateMovie(Integer id, Movie movie) {
        log.info("method updateMovie in MovieService");
        Movie existedMovie = movieRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(
                        "movie does not exists"
                ));
        existedMovie.setName(movie.getName());
        existedMovie.setCreationDate(movie.getCreationDate());
        existedMovie.setUpdatedAt(LocalDateTime.now());
        movieRepository.save(existedMovie);
    }

    @Transactional
    public void deleteMovie(Integer id) {
        log.info("method deleteMovie in MovieService");
        boolean exists = movieRepository.existsById(id);
        if (!exists) {
            throw new IllegalArgumentException("movie does not exists");
        }
        movieRepository.deleteById(id);
    }

    private void enrichMovie(@NotNull Movie movie) {
        movie.setCreatedAt(LocalDateTime.now());
        movie.setUpdatedAt(LocalDateTime.now());
        movie.setCreatedWho("GUEST");
    }
}
