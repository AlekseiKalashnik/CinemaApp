package com.application.Cinema.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@JsonPropertyOrder({"Name", "DOB"})
public class ActorDTO {

    @NotEmpty(message = "name shouldn't be empty")
    @Size(min = 1, max = 72, message = "name should be between 1 and 72 characters")
    @JsonProperty(value = "Name")
    private String name;

    @Min(value = 1, message = "Age should be greater than 1")
    @Max(value = 100, message = "Age should be less than 100")
    @JsonProperty(value = "Age", access = JsonProperty.Access.READ_ONLY)
    private Integer age;

    @Past(message = "Date of birth can't be earlie then current time")
    @JsonProperty(value = "DOB")
    private LocalDate dob;

    public String getName() {
        return name;
    }

    public LocalDate getDob() {
        return dob;
    }
}
