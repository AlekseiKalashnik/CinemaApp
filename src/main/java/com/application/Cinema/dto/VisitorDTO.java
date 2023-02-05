package com.application.Cinema.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;

@Setter
public class VisitorDTO {

    @Column(name = "visitor_name")
    @NotEmpty(message = "name shouldn't be empty")
    @Size(min = 2, max = 32, message = "name should be between 2 and 32 characters")
    private String name;

    @Transient
    private Integer age;

    @Column(name = "email")
    @Email
    @NotEmpty(message = "email shouldn't be empty")
    private String email;

    @Column(name = "dob")
    private LocalDate dob;

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
}
