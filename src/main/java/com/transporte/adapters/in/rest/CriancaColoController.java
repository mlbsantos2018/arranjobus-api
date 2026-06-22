package com.transporte.adapters.in.rest;

import com.transporte.application.dto.request.CriancaColoCreateRequest;
import com.transporte.application.dto.response.CriancaColoResponse;
import com.transporte.application.mapper.CriancaColoMapper;
import com.transporte.application.service.CriancaColoService;
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
@RequestMapping("/api/v1/lap-children")
@AllArgsConstructor
@Tag(name = "Lap Children", description = "Gerenciamento de Crianças de Colo")
@SecurityRequirement(name = "Bearer Authentication")
public class CriancaColoController {

    private final CriancaColoService criancaColoService;
    private final CriancaColoMapper criancaColoMapper;

    @PostMapping
    @Operation(summary = "Criar criança de colo", description = "Registrar uma criança de colo")
    public ResponseEntity<CriancaColoResponse> criar(@Valid @RequestBody CriancaColoCreateRequest request) {
        var criancaColo = criancaColoService.criar(criancaColoMapper.toCriancaColo(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(criancaColoMapper.toResponse(criancaColo));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter criança de colo", description = "Obter uma criança de colo por ID")
    public ResponseEntity<CriancaColoResponse> buscarPorId(@PathVariable UUID id) {
        var criancaColo = criancaColoService.buscarPorId(id);
        return ResponseEntity.ok(criancaColoMapper.toResponse(criancaColo));
    }

    @GetMapping
    @Operation(summary = "Listar crianças", description = "Listar todas as crianças de colo")
    public ResponseEntity<List<CriancaColoResponse>> listar() {
        var criancas = criancaColoService.listar();
        return ResponseEntity.ok(criancas.stream()
                .map(criancaColoMapper::toResponse)
                .collect(Collectors.toList()));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir criança", description = "Excluir uma criança de colo")
    public ResponseEntity<Void> excluir(@PathVariable UUID id) {
        criancaColoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
