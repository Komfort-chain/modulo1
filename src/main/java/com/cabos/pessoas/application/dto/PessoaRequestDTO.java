package com.cabos.pessoas.application.dto;

import java.time.LocalDate;

public record PessoaRequestDTO(
        String nome,
        LocalDate dtNascimento
) {}
