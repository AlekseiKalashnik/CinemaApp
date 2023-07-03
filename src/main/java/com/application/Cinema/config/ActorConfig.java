package com.application.Cinema.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActorConfig {

//    @Bean("visitorRunner")
//    CommandLineRunner commandLineRunner(
//            VisitorRepository repository) {
//        return args -> {
//            Visitor samuel = new Visitor(
//                    "Samuel",
//                    "samuel@gmail.com",
//                    LocalDate.of(2000, Month.JANUARY, 5)
//            );
//
//            Visitor micky = new Visitor(
//                    "Micky",
//                    "micky@gmail.com",
//                    LocalDate.of(1995, Month.JULY, 15)
//            );
//
//            repository.saveAll(
//                    List.of(samuel, micky)
//            );
//        };
//    }

    @Bean("actorMapper")
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}

