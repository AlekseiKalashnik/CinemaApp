package com.application.Cinema.controller;

import com.application.Cinema.dto.ActorDTO;
import com.application.Cinema.model.Actor;
import com.application.Cinema.service.ActorService;
import com.application.Cinema.util.exception_handling.actorException.ActorNotCreatedException;
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
import java.util.stream.Collectors;

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
        return actorService.getActors().stream().map(this::convertToActorDTO)
                .collect(Collectors.toList());
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

    private Actor convertToActor(ActorDTO actorDTO) {
        return modelMapper.map(actorDTO, Actor.class);
    }

    private ActorDTO convertToActorDTO(Actor actor) {
        return modelMapper.map(actor, ActorDTO.class);
    }
}