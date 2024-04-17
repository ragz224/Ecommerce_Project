package com.raghu.productservice.Exceptions;

import com.raghu.productservice.DTOs.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ExceptionDto> handleNotFoundException(NotFoundException notFoundException) {
        return new ResponseEntity<>( new ExceptionDto(HttpStatus.NOT_FOUND,notFoundException.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({InvalidCategoryException.class})
    public ResponseEntity<ExceptionDto> handleNotFoundException(InvalidCategoryException invalidCategoryException) {
        return new ResponseEntity<>( new ExceptionDto(HttpStatus.NOT_FOUND,invalidCategoryException.getMessage()),
                HttpStatus.NOT_FOUND);
    }

}
