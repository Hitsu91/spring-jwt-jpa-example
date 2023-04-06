package com.antsrl.jwtexample.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ExistingUserException extends RuntimeException {
    private final String username;
}
