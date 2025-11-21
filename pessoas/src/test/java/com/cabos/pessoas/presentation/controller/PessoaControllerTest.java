package com.cabos.pessoas.presentation.controller;

import com.cabos.pessoas.application.dto.PessoaDTO;
import com.cabos.pessoas.application.service.PessoaService;
import com.cabos.pessoas.domain.Pessoa;
import com.cabos.pessoas.presentation.mapper.PessoaMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PessoaController.class)
class PessoaControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PessoaService service;

    @Test
    void deveListarPessoas() throws Exception {
        Pessoa p = new Pessoa();
        p.setId(1L);
        p.setNome("Teste");
        p.setDtNascimento(LocalDate.now());
        p.setAtivo(true);

        Page<Pessoa> page = new PageImpl<>(List.of(p));

        when(service.listarAtivos(0)).thenReturn(page);

        mvc.perform(get("/pessoas"))
                .andExpect(status().isOk());
    }

    @Test
    void deveCriarPessoa() throws Exception {
        PessoaDTO dto = new PessoaDTO(null, "Teste", LocalDate.now(), true);
        Pessoa p = PessoaMapper.toEntity(dto);
        p.setId(1L);

        when(service.criar(any(Pessoa.class))).thenReturn(p);

        mvc.perform(post("/pessoas")
                .contentType("application/json")
                .content("{\"nome\":\"Teste\",\"dtNascimento\":\"2000-01-01\",\"ativo\":true}"))
                .andExpect(status().isOk());
    }

    @Test
    void deveDeletarPessoa() throws Exception {
        doNothing().when(service).deletar(1L);

        mvc.perform(delete("/pessoas/1"))
                .andExpect(status().isNoContent());
    }
}
