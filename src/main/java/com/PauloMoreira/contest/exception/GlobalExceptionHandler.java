package com.PauloMoreira.contest.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Manipulador global de exceções para a aplicação.
 *
 * Esta classe é responsável por capturar e tratar exceções lançadas durante o
 * processamento das requisições HTTP. As exceções são mapeadas para códigos de
 * status HTTP apropriados e retornadas com uma mensagem de erro formatada.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Trata exceções do tipo {@link ProcessoNotFoundException}.
     *
     * Retorna uma resposta com o status HTTP 404 (Não Encontrado) e uma
     * mensagem de erro.
     *
     * @param ex A exceção lançada quando o processo judicial não é encontrado.
     * @return Uma resposta com o status HTTP 404 e uma mensagem de erro.
     */
    @ExceptionHandler(ProcessoNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleProcessoNotFoundException(ProcessoNotFoundException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    /**
     * Trata exceções do tipo {@link DuplicateNumeroProcessoException}.
     *
     * Retorna uma resposta com o status HTTP 409 (Conflito) e uma mensagem de
     * erro.
     *
     * @param ex A exceção lançada quando um número de processo é duplicado.
     * @return Uma resposta com o status HTTP 409 e uma mensagem de erro.
     */
    @ExceptionHandler(DuplicateNumeroProcessoException.class)
    public ResponseEntity<Map<String, String>> handleDuplicateNumeroProcessoException(DuplicateNumeroProcessoException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    /**
     * Trata exceções do tipo {@link InvalidInputException}.
     *
     * Retorna uma resposta com o status HTTP 400 (Solicitação Inválida) e uma
     * mensagem de erro.
     *
     * @param ex A exceção lançada quando a entrada fornecida é inválida.
     * @return Uma resposta com o status HTTP 400 e uma mensagem de erro.
     */
    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<Map<String, String>> handleInvalidInputException(InvalidInputException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
