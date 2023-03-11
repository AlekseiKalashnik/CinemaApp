package com.application.Cinema.controller;

import com.application.Cinema.dto.VisitorDTO;
import com.application.Cinema.model.Visitor;
import com.application.Cinema.service.VisitorService;
import com.application.Cinema.util.exception_handling.visitorException.VisitorErrorResponse;
import com.application.Cinema.util.exception_handling.visitorException.VisitorNotCreatedException;
import com.application.Cinema.util.exception_handling.visitorException.VisitorNotFoundException;
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
@RequestMapping(path = "/cinema/visitor")
public class VisitorController {

    private final VisitorService visitorService;
    private final ModelMapper modelMapper;

    @Autowired
    public VisitorController(VisitorService visitorService, @Qualifier("visitorMapper") ModelMapper modelMapper) {
        this.visitorService = visitorService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public List<VisitorDTO> getVisitors() {
        return visitorService.getVisitors().stream().map(this::convertToVisitorDTO)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}")
    public VisitorDTO getVisitor(@PathVariable("id") Integer id) {
        return convertToVisitorDTO(visitorService.getVisitorById(id));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> registerNewVisitor(@RequestBody @Valid VisitorDTO visitorDTO,
                                                         BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            StringBuilder builderErrMessage = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError error : errors) {
                builderErrMessage.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new VisitorNotCreatedException(builderErrMessage.toString());
        }
        visitorService.addNewVisitor(convertToVisitor(visitorDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping(path = "{visitorId}")
    public void deleteVisitor(@PathVariable("visitorId") Integer visitorId) {
        visitorService.deleteVisitor(visitorId);
    }

    @PutMapping(path = "{visitorId}")
    public void updateVisitor(@PathVariable("visitorId") Integer visitorId,
                              @RequestParam(required = false) String name,
                              @RequestParam(required = false) String email) {
        visitorService.updateVisitor(visitorId, name, email);
    }

    private Visitor convertToVisitor(VisitorDTO visitorDTO) {
        return modelMapper.map(visitorDTO, Visitor.class);
    }

    private VisitorDTO convertToVisitorDTO(Visitor visitor) {
        return modelMapper.map(visitor, VisitorDTO.class);
    }

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