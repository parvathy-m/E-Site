package com.project.carro.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class BadRequestException extends RuntimeException {
    public BadRequestException() {
        super(String.valueOf(BAD_REQUEST));
    }

    public BadRequestException(String reason) {

        super(reason);
    }
}
