package com.transporte.adapters.in.rest;

import com.transporte.application.dto.request.LancamentoPagamentoCreateRequest;
import com.transporte.application.dto.request.LancamentoPagamentoUpdateRequest;
import com.transporte.application.dto.request.PagamentoCreateRequest;
import com.transporte.application.dto.response.PagamentoResponse;
import com.transporte.application.mapper.PagamentoMapper;
import com.transporte.application.service.LancamentoPagamentoService;
import com.transporte.application.service.PagamentoService;
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
@RequestMapping("/api/v1/payments")
@AllArgsConstructor
@Tag(name = "Payments", description = "Gerenciamento de Pagamentos")
@SecurityRequirement(name = "Bearer Authentication")
public class PagamentoController {

    private final PagamentoService pagamentoService;
    private final LancamentoPagamentoService lancamentoPagamentoService;
    private final PagamentoMapper pagamentoMapper;

    @PostMapping
    @Operation(summary = "Criar pagamento", description = "Criar um novo pagamento para uma participação")
    public ResponseEntity<PagamentoResponse> criar(@Valid @RequestBody PagamentoCreateRequest request) {
        var pagamento = pagamentoMapper.toPagamento(request);
        var created = pagamentoService.criar(pagamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(pagamentoMapper.toResponse(created));
    }

    @PostMapping("/{paymentId}/installments")
    @Operation(summary = "Registrar parcela", description = "Registrar um lançamento parcial de pagamento")
    public ResponseEntity<PagamentoResponse> registrarParcela(
            @PathVariable UUID paymentId,
            @Valid @RequestBody LancamentoPagamentoCreateRequest request) {
        var lancamento = pagamentoMapper.toLancamentoPagamento(request);
        var updated = pagamentoService.registrarParcela(paymentId, lancamento);
        return ResponseEntity.ok(pagamentoMapper.toResponse(updated));
    }

    @PutMapping("/{paymentId}/installments/{installmentId}")
    @Operation(summary = "Atualizar parcela", description = "Atualizar forma de pagamento ou dados de um lançamento de pagamento")
    public ResponseEntity<PagamentoResponse> atualizarParcela(
            @PathVariable UUID paymentId,
            @PathVariable UUID installmentId,
            @Valid @RequestBody LancamentoPagamentoUpdateRequest request) {
        var lancamentoAtualizado = pagamentoMapper.toLancamentoPagamento(request);
        var updated = lancamentoPagamentoService.atualizarLancamento(paymentId, installmentId, lancamentoAtualizado);
        return ResponseEntity.ok(pagamentoMapper.toResponse(updated));
    }

    @GetMapping("/{participationId}")
    @Operation(summary = "Obter pagamento", description = "Obter pagamento de uma participação")
    public ResponseEntity<PagamentoResponse> buscarPorParticipacao(@PathVariable UUID participationId) {
        var pagamento = pagamentoService.buscarPorParticipacao(participationId);
        return ResponseEntity.ok(pagamentoMapper.toResponse(pagamento));
    }

    @GetMapping("/{paymentId}/history")
    @Operation(summary = "Histórico de pagamento", description = "Obter histórico de lançamentos de um pagamento")
    public ResponseEntity<PagamentoResponse> buscarHistorico(@PathVariable UUID paymentId) {
        var pagamento = pagamentoService.buscarPorId(paymentId);
        return ResponseEntity.ok(pagamentoMapper.toResponse(pagamento));
    }

    @GetMapping("/summary")
    @Operation(summary = "Resumo de pagamentos", description = "Obter resumo de todos os pagamentos")
    public ResponseEntity<List<PagamentoResponse>> listar() {
        var pagamentos = pagamentoService.listar();
        return ResponseEntity.ok(pagamentos.stream()
                .map(pagamentoMapper::toResponse)
                .collect(Collectors.toList()));
    }
}
