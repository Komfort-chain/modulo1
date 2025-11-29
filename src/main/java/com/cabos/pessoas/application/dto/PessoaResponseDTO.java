package com.cabos.pessoas.application.dto;

import java.time.LocalDate;

public record PessoaResponseDTO(
        Long id,
        String nome,
        LocalDate dtNascimento,
        boolean ativo
) {}
