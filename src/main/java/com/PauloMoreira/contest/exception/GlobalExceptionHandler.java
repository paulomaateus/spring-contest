package com.PauloMoreira.contest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ProcessoNotFoundException.class)
    public ResponseEntity<String> handleProcessoNotFoundException(ProcessoNotFoundException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateNumeroProcessoException.class)
    public ResponseEntity<String> handleDuplicateNumeroProcessoException(DuplicateNumeroProcessoException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
