package com.cabos.pessoas.application.dto;

import java.time.LocalDate;

// DTO usado só para troca de dados na API
public record PessoaDTO(Long id, String nome, LocalDate dtNascimento, boolean ativo) {}
