package com.application.Cinema.repository;

import com.application.Cinema.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

    Optional<Movie> findMovieByNameAndAndCreationDate(String name, LocalDate dob);

}
