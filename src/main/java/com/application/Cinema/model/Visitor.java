package com.application.Cinema.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
@Entity
@Table(name = "visitor")
@NoArgsConstructor
@Setter
@ToString
@EqualsAndHashCode
public class Visitor {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(value = "Id")
    private Integer id;

    @Column(name = "visitor_name")
    @NotEmpty(message = "name shouldn't be empty")
    @Size(min = 2, max = 32, message = "name should be between 2 and 32 characters")
    @JsonProperty(value = "Name")
    private String name;

    @Transient
    @JsonProperty(value = "Age")
    @Min(value = 0, message = "Age should be greater than 0")
    private Integer age;

    @Column(name = "email")
    @Email
    @NotEmpty(message = "email shouldn't be empty")
    @JsonProperty(value = "Email")
    private String email;

    @Column(name = "dob")
    @JsonProperty(value = "DOB")
    private LocalDate dob;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_who")
    private String createdWho;

    public Visitor(String name, String email, LocalDate dob) {
        this.name = name;
        this.email = email;
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

    public String getEmail() {
        return email;
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
