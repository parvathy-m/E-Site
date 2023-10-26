package com.project.carro.Configuration;

import com.project.carro.Exceptions.BadRequestException;
import com.project.carro.Exceptions.ErrorMessage;
import com.project.carro.Exceptions.NotFoundException;
import com.project.carro.Exceptions.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class ExceptionController {

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErrorMessage> validationError(MethodArgumentNotValidException ex) {
        List<ErrorMessage> errors = ex.getBindingResult().getFieldErrors().stream()
                .map((x) -> {
                    return ErrorMessage.builder().message(x.getDefaultMessage())
                            .code(String.valueOf(HttpStatus.NOT_FOUND.value()))
                            .build();
                })
                .toList();
        return errors;
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage notFound(NotFoundException ex, WebRequest req) {
        String message = messageSource.getMessage(Objects.requireNonNull(ex.getReason()), null, Locale.ENGLISH);
        String[] reason = message.split("-", 2);
        return ErrorMessage.builder()
                .code(reason[0])
                .message(reason[1])
                .filed(req.getDescription(false))
                .build();
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage badExpressionException(BadRequestException ex, WebRequest req) {
        String reason = messageSource.getMessage(ex.getMessage(), null, Locale.ENGLISH);
        String[] message = reason.split("-", 2);
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code(message[0])
                .message(message[1])
                .filed(req.getDescription(false))
                .build();
        log.info("Exception :  "+errorMessage);
        return errorMessage;
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ErrorMessage unAuthorized(UnauthorizedException ex, WebRequest req) {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("1400")
                .message(ex.getMessage())
                .filed(req.getDescription(false))
                .build();
        log.info("Exception :  "+errorMessage);
        return errorMessage;
    }

}
