package com.nash.assignment.exceptions.handlers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.nash.assignment.dto.response.ExceptionDto;
import com.nash.assignment.exceptions.InformationNotValidException;
import com.nash.assignment.exceptions.ObjectExistException;
import com.nash.assignment.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class DataNotValidHander extends ResponseEntityExceptionHandler {

    @ExceptionHandler({InformationNotValidException.class})
    protected ResponseEntity<ExceptionDto> handleIllegalArgumentException(RuntimeException exception,
            WebRequest request) {
        ExceptionDto error = ExceptionDto.builder().code(400).status("BAD_REQUEST").message(exception.getMessage()).build();
        return new ResponseEntity<ExceptionDto>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ObjectNotFoundException.class})
    protected ResponseEntity<ExceptionDto> handleObjectNotFoundException(RuntimeException exception,
            WebRequest request) {
        ExceptionDto error = ExceptionDto.builder().code(500).status("INTERNAL_SERVER_ERROR").message(exception.getMessage()).build();
        return new ResponseEntity<ExceptionDto>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler({ObjectExistException.class})
    protected ResponseEntity<ExceptionDto> handleObjectExistException(RuntimeException exception,
            WebRequest request) {
        ExceptionDto error = ExceptionDto.builder().code(500).status("INTERNAL_SERVER_ERROR").message(exception.getMessage()).build();
        return new ResponseEntity<ExceptionDto>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String errorMessage = error.getDefaultMessage();
            errors.put("message", errorMessage);
        });
        ExceptionDto exception = ExceptionDto.builder().code(400).status("BAD_REQUEST").message(errors.get("message")).build();

        return new ResponseEntity<Object>(exception, HttpStatus.BAD_REQUEST);
    }

}
