package com.application.Cinema.util.exception_handling.actorException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ActorGlobalExceptionHandler {

    @ExceptionHandler
    private ResponseEntity<ActorErrorResponse> handleException(ActorNotFoundException e) {
        ActorErrorResponse response = new ActorErrorResponse(
                "Actor with this id wasn't found", System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ActorErrorResponse> handleException(ActorNotCreatedException e) {
        ActorErrorResponse response = new ActorErrorResponse(
                e.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
