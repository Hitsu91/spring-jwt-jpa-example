package com.antsrl.jwtexample.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserNotFoundException extends RuntimeException {
    private Integer id;
}
