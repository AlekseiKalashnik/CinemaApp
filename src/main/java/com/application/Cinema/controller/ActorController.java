package com.application.Cinema.controller;

import com.application.Cinema.dto.ActorDTO;
import com.application.Cinema.model.Actor;
import com.application.Cinema.service.ActorService;
import com.application.Cinema.util.exception_handling.actorException.ActorErrorResponse;
import com.application.Cinema.util.exception_handling.actorException.ActorNotCreatedException;
import com.application.Cinema.util.exception_handling.actorException.ActorNotFoundException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/cinema/actors")
public class ActorController {

    private final ActorService actorService;
    private final ModelMapper modelMapper;

    @Autowired
    public ActorController(ActorService actorService, @Qualifier("actorMapper") ModelMapper modelMapper) {
        this.actorService = actorService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<ActorDTO> getActors() {
        return actorService
                .getActors()
                .stream()
                .map(this::convertToActorDTO)
                .toList();
    }

    @GetMapping(value = "/{id}")
    public ActorDTO getActor(@PathVariable("id") Integer id) {
        return convertToActorDTO(actorService.getActorById(id));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createActor(@RequestBody @Valid ActorDTO actorDTO,
                                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder builderErrMessage = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                builderErrMessage.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new ActorNotCreatedException(builderErrMessage.toString());
        }
        actorService.createActor(convertToActor(actorDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public void updateActor(@PathVariable("id") Integer id, @RequestBody ActorDTO actorDTO) {
        actorService.updateActor(id, convertToActor(actorDTO));
    }

    @DeleteMapping(path = "/{id}")
    public void deleteActor(@PathVariable("id") Integer id) {
        actorService.deleteActor(id);
    }

    @ExceptionHandler
    private ResponseEntity<ActorErrorResponse> handleException(ActorNotFoundException e) {
        ActorErrorResponse response = new ActorErrorResponse(
                "Actor with this id was not found",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ActorErrorResponse> handleException(ActorNotCreatedException e) {
        ActorErrorResponse response = new ActorErrorResponse(
                e.getMessage(), System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Actor convertToActor(ActorDTO actorDTO) {
        return modelMapper.map(actorDTO, Actor.class);
    }

    private ActorDTO convertToActorDTO(Actor actor) {
        return modelMapper.map(actor, ActorDTO.class);
    }
}