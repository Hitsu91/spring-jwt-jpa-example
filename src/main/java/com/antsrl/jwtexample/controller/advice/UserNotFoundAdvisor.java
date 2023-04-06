package com.antsrl.jwtexample.controller.advice;

import com.antsrl.jwtexample.dto.response.ErrorResponse;
import com.antsrl.jwtexample.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class UserNotFoundAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCityNotFoundException(
            UserNotFoundException ex, WebRequest request) {

        var response = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Non esiste l'utente con id: " + ex.getId()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
