package com.cabos.pessoas.application.dto;

import java.time.LocalDate;

/**
 * DTO para transferência de dados entre Controller e Cliente.
 * Evita expor diretamente a entidade JPA.
 */
public record PessoaDTO(Long id, String nome, LocalDate dtNascimento, boolean ativo) {}
