package com.antsrl.jwtexample.controller.advice;

import com.antsrl.jwtexample.dto.response.ErrorResponse;
import com.antsrl.jwtexample.exception.DuplicatedKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class DuplicatedKeyAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DuplicatedKeyException.class)
    public ResponseEntity<ErrorResponse> handleCityNotFoundException(
            DuplicatedKeyException ex, WebRequest request) {

        var response = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getError()
        );

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }
}