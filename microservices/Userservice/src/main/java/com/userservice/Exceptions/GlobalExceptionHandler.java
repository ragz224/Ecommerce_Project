package com.userservice.Exceptions;

import com.userservice.Dtos.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({EmailalreadyExistsException.class})
    public ResponseEntity<ExceptionDto> handleEmailalreadyexitsException(EmailalreadyExistsException emailalreadyExistsException) {
        return new ResponseEntity<>( new ExceptionDto(HttpStatus.FOUND,emailalreadyExistsException.getMessage()),
                HttpStatus.FOUND);
    }

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<ExceptionDto> handleUserNotFoundException(UserNotFoundException userNotFoundException) {
            return new ResponseEntity<>(new ExceptionDto(HttpStatus.NOT_FOUND,userNotFoundException.getMessage()),HttpStatus.NOT_FOUND);
    }
}
