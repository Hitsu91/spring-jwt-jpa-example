package com.antsrl.jwtexample.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class DuplicatedKeyException extends RuntimeException {
    private final String error;
}
