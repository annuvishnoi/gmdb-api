package com.galvanize.gmdb.exception;

import com.galvanize.gmdb.model.GmdbExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GmdbExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(GmdbNotFoundException.class)
    public GmdbExceptionResponse handleNotFound(GmdbNotFoundException exception){
        return new GmdbExceptionResponse(exception.getErrorMsg());
    }
}
