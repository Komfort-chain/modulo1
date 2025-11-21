package com.cabos.pessoas.infrastructure.persistence.repository;

import com.cabos.pessoas.domain.Pessoa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

// Consulta só registros ativos
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    Page<Pessoa> findByAtivoTrue(Pageable pageable);
}
