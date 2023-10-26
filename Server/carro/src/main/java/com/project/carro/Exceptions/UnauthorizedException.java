package com.project.carro.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class UnauthorizedException extends ResponseStatusException {

    public UnauthorizedException(HttpStatusCode status) {
        super(status);
    }

    public UnauthorizedException(HttpStatusCode status, String reason) {
        super(status, reason);
    }

    public UnauthorizedException(int rawStatusCode, String reason, Throwable cause) {
        super(rawStatusCode, reason, cause);
    }

    public UnauthorizedException(String reason) {
        super(HttpStatus.UNAUTHORIZED,reason);
    }
}
