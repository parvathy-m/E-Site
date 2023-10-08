package com.project.carro.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class NotFoundException extends ResponseStatusException {
    public NotFoundException(HttpStatusCode status) {
        super(status);
    }

    public NotFoundException(HttpStatusCode status, String reason) {
        super(status, reason);
    }

    public NotFoundException( String reason) {
        super(HttpStatus.NOT_FOUND, reason);
    }

}
