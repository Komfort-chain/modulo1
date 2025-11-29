package com.cabos.pessoas.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "pessoas")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private LocalDate dtNascimento;

    private boolean ativo = true;

    public Pessoa() {
    }

    public Pessoa(Long id, String nome, LocalDate dtNascimento, boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.dtNascimento = dtNascimento;
        this.ativo = ativo;
    }

    public Pessoa(String nome, LocalDate dtNascimento) {
        this(null, nome, dtNascimento, true);
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getDtNascimento() {
        return dtNascimento;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void atualizar(String nome, LocalDate dtNascimento) {
        this.nome = nome;
        this.dtNascimento = dtNascimento;
    }

    public void desativar() {
        this.ativo = false;
    }
}
