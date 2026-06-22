package com.transporte.adapters.in.rest;

import com.transporte.application.dto.request.PessoaCreateRequest;
import com.transporte.application.dto.request.PessoaUpdateRequest;
import com.transporte.application.dto.response.PessoaResponse;
import com.transporte.application.mapper.PessoaMapper;
import com.transporte.application.service.PessoaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/people")
@AllArgsConstructor
@Tag(name = "People", description = "Gerenciamento de Pessoas")
@SecurityRequirement(name = "Bearer Authentication")
public class PessoaController {

    private final PessoaService pessoaService;
    private final PessoaMapper pessoaMapper;

    @PostMapping
    @Operation(summary = "Criar pessoa", description = "Criar uma nova pessoa")
    public ResponseEntity<PessoaResponse> criar(@Valid @RequestBody PessoaCreateRequest request) {
        var pessoa = pessoaService.criar(pessoaMapper.toPessoa(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaMapper.toResponse(pessoa));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter pessoa", description = "Obter uma pessoa por ID")
    public ResponseEntity<PessoaResponse> buscarPorId(@PathVariable UUID id) {
        var pessoa = pessoaService.buscarPorId(id);
        return ResponseEntity.ok(pessoaMapper.toResponse(pessoa));
    }

    @GetMapping
    @Operation(summary = "Listar pessoas", description = "Listar todas as pessoas")
    public ResponseEntity<List<PessoaResponse>> listar() {
        var pessoas = pessoaService.listar();
        return ResponseEntity.ok(pessoas.stream()
                .map(pessoaMapper::toResponse)
                .collect(Collectors.toList()));
    }

    @GetMapping("/search")
    @Operation(summary = "Pesquisar pessoas", description = "Pesquisar pessoas por termo")
    public ResponseEntity<List<PessoaResponse>> pesquisar(@RequestParam String termo) {
        var pessoas = pessoaService.pesquisar(termo);
        return ResponseEntity.ok(pessoas.stream()
                .map(pessoaMapper::toResponse)
                .collect(Collectors.toList()));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar pessoa", description = "Atualizar uma pessoa existente")
    public ResponseEntity<PessoaResponse> atualizar(@PathVariable UUID id, @Valid @RequestBody PessoaUpdateRequest request) {
        var pessoaExistente = pessoaService.buscarPorId(id);
        var pessoaAtualizada = pessoaService.atualizar(id, pessoaMapper.toPessoa(id, request, pessoaExistente));
        return ResponseEntity.ok(pessoaMapper.toResponse(pessoaAtualizada));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir pessoa", description = "Excluir uma pessoa")
    public ResponseEntity<Void> excluir(@PathVariable UUID id) {
        pessoaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
