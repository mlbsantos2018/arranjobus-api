package com.transporte.adapters.in.rest;

import com.transporte.application.dto.request.ParticipacaoCreateRequest;
import com.transporte.application.dto.response.ParticipacaoResponse;
import com.transporte.application.mapper.ParticipacaoMapper;
import com.transporte.application.service.ParticipacaoService;
import com.transporte.domain.enums.StatusPagamento;
import com.transporte.domain.ports.out.PagamentoRepositoryPort;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.transporte.domain.enums.DiaEvento;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/participations")
@AllArgsConstructor
@Tag(name = "Participations", description = "Gerenciamento de Participações")
@SecurityRequirement(name = "Bearer Authentication")
public class ParticipacaoController {

    private final ParticipacaoService participacaoService;
    private final ParticipacaoMapper participacaoMapper;
    private final PagamentoRepositoryPort pagamentoRepository;

    @PostMapping
    @Operation(summary = "Criar participação", description = "Adicionar um participante em um evento")
    public ResponseEntity<ParticipacaoResponse> criar(@Valid @RequestBody ParticipacaoCreateRequest request) {
        var participacao = participacaoService.adicionar(participacaoMapper.toParticipacao(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(participacaoMapper.toResponse(participacao));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover participação", description = "Remover um participante de um evento")
    public ResponseEntity<Void> remover(
            @PathVariable UUID id,
            @RequestParam(defaultValue = "false") boolean keepPayment) {
        participacaoService.remover(id, keepPayment);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/events/{eventId}/participants")
    @Operation(summary = "Listar participantes", description = "Listar participantes de um evento")
    public ResponseEntity<List<ParticipacaoResponse>> listarPorEvento(@PathVariable UUID eventId) {
        var participacoes = participacaoService.listarPorEvento(eventId);
        return ResponseEntity.ok(participacoes.stream()
                .map(participacao -> participacaoMapper.toResponse(
                        participacao,
                        pagamentoRepository.buscarPorParticipacaoId(participacao.getId())
                                .map(pagamento -> pagamento.getStatus())
                                .orElse(StatusPagamento.PENDENTE)))
                .collect(Collectors.toList()));
    }

    @Operation(summary = "Listar participantes por dia", description = "Listar participantes de um evento em um dia específico")
    @GetMapping("/events/{eventId}/participants/day/{dia}")
    public ResponseEntity<List<ParticipacaoResponse>> listarPorDia(
            @PathVariable UUID eventId,
            @PathVariable DiaEvento dia) {
        var participacoes = participacaoService.listarPorEventoEDia(eventId, dia);

        return ResponseEntity.ok(
                participacoes.stream()
                        .map(participacao -> participacaoMapper.toResponse(
                                participacao,
                                pagamentoRepository.buscarPorParticipacaoId(participacao.getId())
                                        .map(pagamento -> pagamento.getStatus())
                                        .orElse(StatusPagamento.PENDENTE)))
                        .collect(Collectors.toList()));
    }
}
