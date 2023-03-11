package com.application.Cinema.controller;

import com.application.Cinema.dto.MovieDTO;
import com.application.Cinema.model.Movie;
import com.application.Cinema.service.MovieService;
import com.application.Cinema.util.exception_handling.movieException.MovieErrorResponse;
import com.application.Cinema.util.exception_handling.movieException.MovieNotCreatedException;
import com.application.Cinema.util.exception_handling.movieException.MovieNotFoundException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/cinema/movie")
public class MovieController {

    private final MovieService movieService;
    private final ModelMapper modelMapper;

    @Autowired
    public MovieController(MovieService movieService, @Qualifier("movieMapper") ModelMapper modelMapper) {
        this.movieService = movieService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public List<MovieDTO> getMovies() {
        return movieService.getMovies().stream().map(this::convertToMovieDTO)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}")
    public MovieDTO getMovie(@PathVariable("id") Integer id) {
        return convertToMovieDTO(movieService.getMovieById(id));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> registerNewMovie(@RequestBody @Valid MovieDTO movieDTO,
                                                         BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            StringBuilder builderErrMessage = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError error : errors) {
                builderErrMessage.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new MovieNotCreatedException(builderErrMessage.toString());
        }
        movieService.addNewMovie(convertToMovie(movieDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping(path = "{movieId}")
    public void deleteMovie(@PathVariable("movieId") Integer movieId) {
        movieService.deleteMovie(movieId);
    }

    @PutMapping(path = "{movieId}")
    public void updateMovie(@PathVariable("movieId") Integer movieId,
                              @RequestParam(required = false) String name) {
        movieService.updateMovie(movieId, name);
    }

    private Movie convertToMovie(MovieDTO movieDTO) {
        return modelMapper.map(movieDTO, Movie.class);
    }

    private MovieDTO convertToMovieDTO(Movie movie) {
        return modelMapper.map(movie, MovieDTO.class);
    }

    @ExceptionHandler
    private ResponseEntity<MovieErrorResponse> handleException(MovieNotFoundException e) {
        MovieErrorResponse response = new MovieErrorResponse(
                "Movie with this id wasn't found", System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<MovieErrorResponse> handleException(MovieNotCreatedException e) {
        MovieErrorResponse response = new MovieErrorResponse(
                e.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
