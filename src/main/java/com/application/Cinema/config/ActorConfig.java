package com.application.Cinema.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActorConfig {

    @Bean("actorMapper")
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}

