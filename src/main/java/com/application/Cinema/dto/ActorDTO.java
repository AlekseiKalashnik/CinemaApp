package com.application.Cinema.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@JsonPropertyOrder({"Name", "DOB"})
public class ActorDTO {

    @JsonProperty(value = "Name")
    private String name;

    @JsonProperty(value = "Age", access = JsonProperty.Access.READ_ONLY)
    private Integer age;

    @JsonProperty(value = "DOB")
    private LocalDate dob;

    public String getName() {
        return name;
    }

    public LocalDate getDob() {
        return dob;
    }
}
