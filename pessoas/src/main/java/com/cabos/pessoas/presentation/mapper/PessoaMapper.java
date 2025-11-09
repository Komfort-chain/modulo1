package com.cabos.pessoas.presentation.mapper;

import com.cabos.pessoas.application.dto.PessoaDTO;
import com.cabos.pessoas.domain.Pessoa;

/**
 * Mapper responsável por converter entre Entity e DTO.
 */
public class PessoaMapper {

    public static PessoaDTO toDto(Pessoa p) {
        return new PessoaDTO(p.getId(), p.getNome(), p.getDtNascimento(), p.isAtivo());
    }

    public static Pessoa toEntity(PessoaDTO dto) {
        Pessoa p = new Pessoa();
        p.setId(dto.id());
        p.setNome(dto.nome());
        p.setDtNascimento(dto.dtNascimento());
        p.setAtivo(dto.ativo());
        return p;
    }
}
