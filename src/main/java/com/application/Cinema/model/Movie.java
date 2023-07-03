package com.application.Cinema.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "movies")
@NoArgsConstructor
@Setter
@ToString
@EqualsAndHashCode
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "movie_name")
    @NotEmpty(message = "name shouldn't be empty")
    @Size(min = 2, max = 32, message = "name should be between 2 and 32 characters")
    private String name;

    @Past(message = "Date of creation can't be earlie then current time")
    private LocalDate creationDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Movie(String name, LocalDate creationDate) {
        this.name = name;
        this.creationDate = creationDate;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
