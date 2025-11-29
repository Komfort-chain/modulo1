package com.cabos.pessoas.application.service;

import com.cabos.pessoas.domain.Pessoa;
import com.cabos.pessoas.domain.exception.PessoaNaoEncontradaException;
import com.cabos.pessoas.infrastructure.repository.PessoaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PessoaService {

    private final PessoaRepository repository;

    public PessoaService(PessoaRepository repository) {
        this.repository = repository;
    }

    public Pessoa criar(String nome, LocalDate dtNascimento) {
        Pessoa pessoa = new Pessoa(nome, dtNascimento);
        return repository.save(pessoa);
    }

    public Page<Pessoa> listarAtivos(int page, int size) {
        return repository.findByAtivoTrue(PageRequest.of(page, size));
    }

    public Pessoa buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new PessoaNaoEncontradaException(id));
    }

    public Pessoa atualizar(Long id, String nome, LocalDate dtNascimento) {
        Pessoa pessoa = buscarPorId(id);
        pessoa.atualizar(nome, dtNascimento);
        return repository.save(pessoa);
    }

    public void deletar(Long id) {
        Pessoa pessoa = buscarPorId(id);
        pessoa.desativar();      
        repository.save(pessoa);
    }
}
