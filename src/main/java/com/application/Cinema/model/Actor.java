package com.application.Cinema.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

@Entity
@Table(name = "actors")
@NoArgsConstructor
@Setter
@ToString
@EqualsAndHashCode
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "actor_name")
    @NotEmpty(message = "name shouldn't be empty")
    @Size(min = 1, max = 72, message = "name should be between 1 and 72 characters")
    private String name;

    @Min(value = 1, message = "Age should be greater than 1")
    @Max(value = 100, message = "Age should be less than 100")
    private Integer age;

    @Past(message = "Date of birth can't be earlie then current time")
    @JsonProperty(value = "DOB")
    private LocalDate dob;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String createdWho;

    public Actor(String name, LocalDate dob) {
        this.name = name;
        this.dob = dob;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return Period.between(this.dob, LocalDate.now()).getYears();
    }

    public LocalDate getDob() {
        return dob;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public String getCreatedWho() {
        return createdWho;
    }
}
