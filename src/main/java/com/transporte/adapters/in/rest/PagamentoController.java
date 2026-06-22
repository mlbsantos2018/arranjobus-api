package com.transporte.adapters.in.rest;

import com.transporte.application.dto.response.PagamentoResponse;
import com.transporte.application.mapper.PagamentoMapper;
import com.transporte.application.service.PagamentoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
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
    private final PagamentoMapper pagamentoMapper;

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
