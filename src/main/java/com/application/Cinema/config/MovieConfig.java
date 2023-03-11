package com.application.Cinema.config;

import com.application.Cinema.model.Movie;
import com.application.Cinema.repository.MovieRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class MovieConfig {

    @Bean("movieRunner")
    CommandLineRunner commandLineRunner(
            MovieRepository repository) {
        return args -> {
            Movie matrix = new Movie(
                    "Matrix",
                    LocalDate.of(1995, Month.MARCH, 31)
            );

            Movie warrior = new Movie(
                    "Warrior",
                    LocalDate.of(2011, Month.SEPTEMBER, 9)
            );

            repository.saveAll(
                    List.of(matrix, warrior)
            );
        };
    }

    @Bean("movieMapper")
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
