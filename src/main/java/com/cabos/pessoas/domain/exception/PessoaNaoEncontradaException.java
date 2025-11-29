package com.cabos.pessoas.domain.exception;

public class PessoaNaoEncontradaException extends RuntimeException {

    public PessoaNaoEncontradaException(Long id) {
        super("Pessoa não encontrada com ID: " + id);
    }
}
