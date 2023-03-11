package com.application.Cinema.util.exception_handling.movieException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class MovieErrorResponse {
    private String message;
    private long timestamp;
}
