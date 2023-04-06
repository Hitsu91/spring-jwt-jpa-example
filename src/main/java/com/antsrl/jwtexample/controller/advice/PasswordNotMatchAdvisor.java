package com.antsrl.jwtexample.controller.advice;

import com.antsrl.jwtexample.dto.response.ErrorResponse;
import com.antsrl.jwtexample.exception.PasswordNotMatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class PasswordNotMatchAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PasswordNotMatchException.class)
    public ResponseEntity<ErrorResponse> handleCityNotFoundException(
            PasswordNotMatchException ex, WebRequest request) {
        var response = new ErrorResponse(
                HttpStatus.FORBIDDEN.value(),
                "Le due password non sono uguali"
        );

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }
}