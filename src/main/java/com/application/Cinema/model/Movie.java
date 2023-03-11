package com.application.Cinema.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "movie")
@NoArgsConstructor
@Setter
@ToString
@EqualsAndHashCode
public class Movie {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(value = "Id")
    private Integer id;

    @Column(name = "movie_name")
    @NotEmpty(message = "name shouldn't be empty")
    @Size(min = 2, max = 32, message = "name should be between 2 and 32 characters")
    @JsonProperty(value = "Name")
    private String name;

    @Column(name = "creation_date")
    @JsonProperty(value = "Creation date")
    private LocalDate creationDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_who")
    private String createdWho;

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

    public String getCreatedWho() {
        return createdWho;
    }
}
