package com.application.Cinema.controller;

import com.application.Cinema.dto.MovieDTO;
import com.application.Cinema.model.Movie;
import com.application.Cinema.service.MovieService;
import com.application.Cinema.util.exception_handling.movieException.MovieNotCreatedException;
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
        if (bindingResult.hasErrors()) {
            StringBuilder builderErrMessage = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
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

    @DeleteMapping(path = "{id}")
    public void deleteMovie(@PathVariable("id") Integer movieId) {
        movieService.deleteMovie(movieId);
    }

    @PutMapping(path = "{id}")
    public void updateMovie(@PathVariable("id") Integer movieId,
                            @RequestParam(required = false) String name) {
        movieService.updateMovie(movieId, name);
    }

    private Movie convertToMovie(MovieDTO movieDTO) {
        return modelMapper.map(movieDTO, Movie.class);
    }

    private MovieDTO convertToMovieDTO(Movie movie) {
        return modelMapper.map(movie, MovieDTO.class);
    }
}
