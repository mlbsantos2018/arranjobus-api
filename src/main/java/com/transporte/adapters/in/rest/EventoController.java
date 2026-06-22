package com.transporte.adapters.in.rest;

import com.transporte.application.dto.request.EventoCreateRequest;
import com.transporte.application.dto.response.EventoResponse;
import com.transporte.application.mapper.EventoMapper;
import com.transporte.application.service.EventoService;
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
@RequestMapping("/api/v1/events")
@AllArgsConstructor
@Tag(name = "Events", description = "Gerenciamento de Eventos")
public class EventoController {

    private final EventoService eventoService;
    private final EventoMapper eventoMapper;

    @PostMapping
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Criar evento", description = "Criar um novo evento")
    public ResponseEntity<EventoResponse> criar(@Valid @RequestBody EventoCreateRequest request) {
        var evento = eventoService.criar(eventoMapper.toEvento(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(eventoMapper.toResponse(evento));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter evento", description = "Obter um evento por ID")
    public ResponseEntity<EventoResponse> buscarPorId(@PathVariable UUID id) {
        var evento = eventoService.buscarPorId(id);
        return ResponseEntity.ok(eventoMapper.toResponse(evento));
    }

    @GetMapping
    @Operation(summary = "Listar eventos", description = "Listar todos os eventos")
    public ResponseEntity<List<EventoResponse>> listar() {
        var eventos = eventoService.listar();
        return ResponseEntity.ok(eventos.stream()
                .map(eventoMapper::toResponse)
                .collect(Collectors.toList()));
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Atualizar evento", description = "Atualizar um evento existente")
    public ResponseEntity<EventoResponse> atualizar(@PathVariable UUID id, @Valid @RequestBody EventoCreateRequest request) {
        var eventoAtualizado = eventoService.atualizar(id, eventoMapper.toEvento(request));
        return ResponseEntity.ok(eventoMapper.toResponse(eventoAtualizado));
    }

    @PatchMapping("/{id}/close")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Encerrar evento", description = "Encerrar um evento")
    public ResponseEntity<EventoResponse> encerrar(@PathVariable UUID id) {
        var eventoEncerrado = eventoService.encerrar(id);
        return ResponseEntity.ok(eventoMapper.toResponse(eventoEncerrado));
    }

    @PatchMapping("/{id}/archive")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Arquivar evento", description = "Arquivar um evento")
    public ResponseEntity<EventoResponse> arquivar(@PathVariable UUID id) {
        var eventoArquivado = eventoService.arquivar(id);
        return ResponseEntity.ok(eventoMapper.toResponse(eventoArquivado));
    }
}
