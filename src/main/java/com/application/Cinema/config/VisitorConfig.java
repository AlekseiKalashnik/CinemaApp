package com.application.Cinema.config;

import com.application.Cinema.model.Visitor;
import com.application.Cinema.repository.VisitorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class VisitorConfig {

    @Bean
    CommandLineRunner commandLineRunner(
            VisitorRepository repository) {
        return args -> {
            Visitor samuel = new Visitor(
                    "Samuel",
                    "samuel@gmail.com",
                    LocalDate.of(2000, Month.JANUARY, 5)
            );

            Visitor micky = new Visitor(
                    "Micky",
                    "micky@gmail.com",
                    LocalDate.of(1995, Month.JULY, 15)
            );

            repository.saveAll(
                    List.of(samuel, micky)
            );
        };
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}

