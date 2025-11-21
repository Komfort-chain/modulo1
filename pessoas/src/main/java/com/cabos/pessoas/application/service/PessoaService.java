package com.cabos.pessoas.application.service;

import com.cabos.pessoas.domain.Pessoa;
import com.cabos.pessoas.infrastructure.persistence.repository.PessoaRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class PessoaService {

    private static final Logger log = LoggerFactory.getLogger(PessoaService.class);
    private final PessoaRepository repo;

    public PessoaService(PessoaRepository repo) {
        this.repo = repo;
    }

    // Apenas lista registros ativos
    public Page<Pessoa> listarAtivos(int page) {
        log.info("Listando ativos (page={})", page);
        return repo.findByAtivoTrue(PageRequest.of(page, 10));
    }

    public Pessoa criar(Pessoa p) {
        log.info("Criando {}", p.getNome());
        return repo.save(p);
    }

    public Pessoa buscar(Long id) {
        log.info("Buscando ID {}", id);
        return repo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Pessoa não encontrada"));
    }

    public Pessoa atualizar(Long id, Pessoa dados) {
        log.info("Atualizando ID {}", id);
        Pessoa p = buscar(id);

        p.setNome(dados.getNome());
        p.setDtNascimento(dados.getDtNascimento());
        p.setAtivo(dados.isAtivo());

        return repo.save(p);
    }

    public void deletar(Long id) {
        log.info("Deletando ID {}", id);
        repo.deleteById(id);
    }
}
