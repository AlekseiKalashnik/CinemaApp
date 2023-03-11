package com.application.Cinema.util.exception_handling;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class VisitorErrorResponse {
    private String message;
    private long timestamp;
}
