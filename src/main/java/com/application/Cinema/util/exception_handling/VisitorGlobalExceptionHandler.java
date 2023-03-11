package com.application.Cinema.util.exception_handling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class VisitorGlobalExceptionHandler {

    @ExceptionHandler
    private ResponseEntity<VisitorErrorResponse> handleException(VisitorNotFoundException e) {
        VisitorErrorResponse response = new VisitorErrorResponse(
                "Person with this id wasn't found", System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<VisitorErrorResponse> handleException(VisitorNotCreatedException e) {
        VisitorErrorResponse response = new VisitorErrorResponse(
                e.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
