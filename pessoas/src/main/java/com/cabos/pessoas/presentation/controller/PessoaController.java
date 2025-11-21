package com.cabos.pessoas.presentation.controller;

import com.cabos.pessoas.application.dto.PessoaDTO;
import com.cabos.pessoas.application.service.PessoaService;
import com.cabos.pessoas.domain.Pessoa;
import com.cabos.pessoas.presentation.mapper.PessoaMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    private static final Logger log = LoggerFactory.getLogger(PessoaController.class);
    private final PessoaService service;

    public PessoaController(PessoaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<PessoaDTO>> listar(@RequestParam(defaultValue = "0") int page) {
        Page<Pessoa> pessoas = service.listarAtivos(page);

        var dtoPage = new PageImpl<>(
                pessoas.getContent()
                        .stream()
                        .map(PessoaMapper::toDto)
                        .collect(Collectors.toList()),
                pessoas.getPageable(),
                pessoas.getTotalElements()
        );

        log.info("Listando (page={})", page);
        return ResponseEntity.ok(dtoPage);
    }

    @PostMapping
    public ResponseEntity<PessoaDTO> criar(@RequestBody PessoaDTO dto) {
        Pessoa nova = service.criar(PessoaMapper.toEntity(dto));
        log.info("Criada {}", nova.getNome());
        return ResponseEntity.ok(PessoaMapper.toDto(nova));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaDTO> buscar(@PathVariable Long id) {
        Pessoa p = service.buscar(id);
        log.info("Buscando ID {}", id);
        return ResponseEntity.ok(PessoaMapper.toDto(p));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PessoaDTO> atualizar(@PathVariable Long id, @RequestBody PessoaDTO dto) {
        Pessoa p = service.atualizar(id, PessoaMapper.toEntity(dto));
        log.info("Atualizada {}", p.getNome());
        return ResponseEntity.ok(PessoaMapper.toDto(p));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        log.info("Deletada ID {}", id);
        return ResponseEntity.noContent().build();
    }
}
