package com.nocountry.api.config;

import com.nocountry.api.exception.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleResourceNotFoundException(ResourceNotFoundException e) {
        CustomErrorResponse customErrorResponse = new CustomErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());
        return new ResponseEntity<>(customErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RegisterException.class)
    public ResponseEntity<CustomErrorResponse> handleRegisterException(RegisterException e) {
        CustomErrorResponse customErrorResponse = new CustomErrorResponse(HttpStatus.CONFLICT.value(), e.getMessage());
        return new ResponseEntity<>(customErrorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotValidDatetimeException.class)
    public ResponseEntity<CustomErrorResponse> handleNotValidDateException(NotValidDatetimeException e) {
        CustomErrorResponse customErrorResponse = new CustomErrorResponse(HttpStatus.BAD_REQUEST.value(),
                e.getMessage());
        return new ResponseEntity<>(customErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AppointmentException.class)
    public ResponseEntity<CustomErrorResponse> handleNotValidAppointmentException(AppointmentException e) {
        CustomErrorResponse customErrorResponse = new CustomErrorResponse(HttpStatus.BAD_REQUEST.value(),
                e.getMessage());
        return new ResponseEntity<>(customErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<CustomErrorResponse> handleLoginException(LoginException e) {
        CustomErrorResponse customErrorResponse = new CustomErrorResponse(HttpStatus.UNAUTHORIZED.value(),
                e.getMessage());
        return new ResponseEntity<>(customErrorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleEntityNotFoundException(EntityNotFoundException e) {
        CustomErrorResponse customErrorResponse = new CustomErrorResponse(HttpStatus.BAD_REQUEST.value(),
                e.getMessage());
        return new ResponseEntity<>(customErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusinessSchedulesLimitException.class)
    public ResponseEntity<CustomErrorResponse> handleBusinessSchedulesLimitException(BusinessSchedulesLimitException e){
        CustomErrorResponse customErrorResponse = new CustomErrorResponse(HttpStatus.FORBIDDEN.value(), e.getMessage());
        return new ResponseEntity<>(customErrorResponse, HttpStatus.FORBIDDEN);
    }
}
