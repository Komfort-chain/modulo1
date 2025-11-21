package com.cabos.pessoas.presentation.mapper;

import com.cabos.pessoas.application.dto.PessoaDTO;
import com.cabos.pessoas.domain.Pessoa;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PessoaMapperTest {

    @Test
    void deveConverterEntityParaDto() {
        Pessoa p = new Pessoa();
        p.setId(1L);
        p.setNome("João");
        p.setDtNascimento(LocalDate.of(2000, 1, 1));
        p.setAtivo(true);

        PessoaDTO dto = PessoaMapper.toDto(p);

        assertEquals(1L, dto.id());
        assertEquals("João", dto.nome());
    }

    @Test
    void deveConverterDtoParaEntity() {
        PessoaDTO dto = new PessoaDTO(2L, "Maria", LocalDate.of(1990, 5, 10), true);

        Pessoa p = PessoaMapper.toEntity(dto);

        assertEquals(2L, p.getId());
        assertEquals("Maria", p.getNome());
    }
}
