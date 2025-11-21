package com.cabos.pessoas.application.service;

import com.cabos.pessoas.domain.Pessoa;
import com.cabos.pessoas.infrastructure.persistence.repository.PessoaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PessoaServiceTest {

    private PessoaRepository repo;
    private PessoaService service;

    @BeforeEach
    void setup() {
        repo = Mockito.mock(PessoaRepository.class);
        service = new PessoaService(repo);
    }

    @Test
    void deveListarSomenteAtivos() {
        Page<Pessoa> page = new PageImpl<>(List.of(new Pessoa()));
        when(repo.findByAtivoTrue(any(Pageable.class))).thenReturn(page);

        Page<Pessoa> result = service.listarAtivos(0);

        assertEquals(1, result.getContent().size());
    }

    @Test
    void deveCriarPessoa() {
        Pessoa p = new Pessoa();
        p.setNome("Teste");

        when(repo.save(p)).thenReturn(p);

        Pessoa result = service.criar(p);

        assertEquals("Teste", result.getNome());
    }

    @Test
    void deveBuscarPorIdExistente() {
        Pessoa p = new Pessoa();
        p.setId(1L);

        when(repo.findById(1L)).thenReturn(Optional.of(p));

        Pessoa result = service.buscar(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void deveAtualizarPessoa() {
        Pessoa original = new Pessoa();
        original.setId(1L);
        original.setNome("Antes");
        original.setDtNascimento(LocalDate.now());

        Pessoa dados = new Pessoa();
        dados.setNome("Depois");
        dados.setDtNascimento(LocalDate.now());
        dados.setAtivo(true);

        when(repo.findById(1L)).thenReturn(Optional.of(original));
        when(repo.save(any(Pessoa.class))).thenReturn(original);

        Pessoa result = service.atualizar(1L, dados);

        assertEquals("Depois", result.getNome());
    }

    @Test
    void deveDeletar() {
        doNothing().when(repo).deleteById(1L);

        service.deletar(1L);

        verify(repo, times(1)).deleteById(1L);
    }
}
