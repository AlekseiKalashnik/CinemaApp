package com.application.Cinema.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@JsonPropertyOrder({"Name", "Creation date"})
public class MovieDTO {

    @NotEmpty(message = "name shouldn't be empty")
    @Size(min = 2, max = 32, message = "name should be between 2 and 32 characters")
    @JsonProperty(value = "Name")
    private String name;

    @Past(message = "Date of creation can't be earlie then current time")
    @JsonProperty(value = "Creation date")
    private LocalDate creationDate;
}
