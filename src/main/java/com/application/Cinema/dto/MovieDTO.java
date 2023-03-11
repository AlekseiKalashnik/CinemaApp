package com.application.Cinema.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Setter;

import java.time.LocalDate;

@Setter
public class MovieDTO {

    @Column(name = "movie_name")
    @NotEmpty(message = "name shouldn't be empty")
    @Size(min = 2, max = 32, message = "name should be between 2 and 32 characters")
    @JsonProperty(value = "Name")
    private String name;

    @Column(name = "creation_date")
    @JsonProperty(value = "Creation date")
    private LocalDate creationDate;

    public String getName() {
        return name;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }
}
