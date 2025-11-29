package com.cabos.pessoas.api;

import com.cabos.pessoas.application.dto.PessoaRequestDTO;
import com.cabos.pessoas.application.dto.PessoaResponseDTO;
import com.cabos.pessoas.application.mapper.PessoaMapper;
import com.cabos.pessoas.application.service.PessoaService;
import com.cabos.pessoas.domain.Pessoa;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pessoas")
public class PessoaController {

    private final PessoaService service;

    public PessoaController(PessoaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PessoaResponseDTO> criar(@RequestBody PessoaRequestDTO dto) {
        Pessoa pessoa = service.criar(dto.nome(), dto.dtNascimento());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(PessoaMapper.toResponse(pessoa));
    }

    @GetMapping
    public ResponseEntity<Page<PessoaResponseDTO>> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<PessoaResponseDTO> result = service
                .listarAtivos(page, size)
                .map(PessoaMapper::toResponse);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaResponseDTO> buscarPorId(@PathVariable Long id) {
        Pessoa pessoa = service.buscarPorId(id);
        return ResponseEntity.ok(PessoaMapper.toResponse(pessoa));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PessoaResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody PessoaRequestDTO dto
    ) {
        Pessoa pessoa = service.atualizar(id, dto.nome(), dto.dtNascimento());
        return ResponseEntity.ok(PessoaMapper.toResponse(pessoa));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
