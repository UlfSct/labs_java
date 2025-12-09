package com.example.lab6.errors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException e) {
        ServerError errorObject = new ServerError(e.getMessage());
        return new ResponseEntity<>(errorObject.getErrors(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleInvalidDataException(InvalidDataException e) {
        ServerError errorObject = new ServerError(e.getFieldErrors());
        return new ResponseEntity<>(errorObject.getErrors(), HttpStatus.BAD_REQUEST);
    }
}
