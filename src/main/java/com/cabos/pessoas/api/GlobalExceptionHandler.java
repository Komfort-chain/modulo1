package com.cabos.pessoas.api;

import com.cabos.pessoas.domain.exception.PessoaNaoEncontradaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PessoaNaoEncontradaException.class)
    public ResponseEntity<ErrorResponse> handlePessoaNaoEncontrada(PessoaNaoEncontradaException ex) {
        ErrorResponse body = new ErrorResponse(
                Instant.now(),
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    public record ErrorResponse(
            Instant timestamp,
            int status,
            String message
    ) {}
}
