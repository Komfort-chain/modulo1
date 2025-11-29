package com.cabos.pessoas.application.mapper;

import com.cabos.pessoas.application.dto.PessoaResponseDTO;
import com.cabos.pessoas.domain.Pessoa;

public final class PessoaMapper {

    private PessoaMapper() {
    }

    public static PessoaResponseDTO toResponse(Pessoa pessoa) {
        return new PessoaResponseDTO(
                pessoa.getId(),
                pessoa.getNome(),
                pessoa.getDtNascimento(),
                pessoa.isAtivo()
        );
    }
}
