package com.application.Cinema.service;

import com.application.Cinema.model.Movie;
import com.application.Cinema.repository.MovieRepository;
import com.application.Cinema.util.exception_handling.movieException.MovieNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
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

    public void addNewMovie(Movie movie) {
        log.info("method addNewMovie in MovieService");
        //throw exception if movie has already existed in DB
        enrichMovie(movie);
        movieRepository.save(movie);
    }

    public void deleteMovie(Integer movieId) {
        log.info("method deleteMovie in MovieService");
        boolean exists = movieRepository.existsById(movieId);
        if(!exists) {
            throw new IllegalArgumentException("movie with id " +
                    movieId + " does not exists");
        }
        movieRepository.deleteById(movieId);
    }

    @Transactional
    public void updateMovie(Integer movieId, String name) {
        log.info("method updateMovie in MovieService");
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new IllegalStateException(
                        "movie with id " + movieId + " does not exists"
                ));

        if(name != null && name.length() > 0 && !Objects.equals(movie.getName(), name)) {
            movie.setName(name);
        }
        movie.setUpdatedAt(LocalDateTime.now());
    }

    private void enrichMovie(Movie movie) {
        movie.setCreatedAt(LocalDateTime.now());
        movie.setUpdatedAt(LocalDateTime.now());
        movie.setCreatedWho("ADMIN");
    }
}
