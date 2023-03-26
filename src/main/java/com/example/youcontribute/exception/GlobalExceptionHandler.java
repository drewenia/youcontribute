package com.example.youcontribute.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/* ControllerAdvice spring'de kullanilan Exception'lari handle etmek icin kullaniliyor */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicatedRepositoryException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorResponse handleDuplicateRepositoryException(DuplicatedRepositoryException exception, HttpServletRequest request) {
        return ErrorResponse.builder().message(exception.getMessage()).build();
    }
}
