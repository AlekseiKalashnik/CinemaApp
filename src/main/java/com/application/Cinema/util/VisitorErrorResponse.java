package com.application.Cinema.util;

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
