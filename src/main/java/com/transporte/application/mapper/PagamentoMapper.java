package com.transporte.application.mapper;

import com.transporte.application.dto.request.LancamentoPagamentoCreateRequest;
import com.transporte.application.dto.request.LancamentoPagamentoUpdateRequest;
import com.transporte.application.dto.request.PagamentoCreateRequest;
import com.transporte.application.dto.response.PagamentoResponse;
import com.transporte.application.dto.response.LancamentoPagamentoResponse;
import com.transporte.domain.model.Pagamento;
import com.transporte.domain.model.LancamentoPagamento;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Component
public class PagamentoMapper {

    public Pagamento toPagamento(PagamentoCreateRequest request) {
        return Pagamento.builder()
                .participacaoId(request.getParticipacaoId())
                .valorTotal(request.getValorTotal())
                .valorPago(null)
                .saldoDevedor(request.getValorTotal())
                .status(null) // Status is set by service
                .lancamentos(null) // Initially empty
                .build();
    }

    public LancamentoPagamento toLancamentoPagamento(LancamentoPagamentoCreateRequest request) {
        return LancamentoPagamento.builder()
                .valor(request.getValor())
                .formaPagamento(request.getFormaPagamento())
                .dataPagamento(request.getDataPagamento())
                .observacao(request.getObservacao())
                .dia(request.getDia())
                .build();
    }

    public LancamentoPagamento toLancamentoPagamento(LancamentoPagamentoUpdateRequest request) {
        return LancamentoPagamento.builder()
                .valor(request.getValor())
                .formaPagamento(request.getFormaPagamento())
                .dataPagamento(request.getDataPagamento())
                .observacao(request.getObservacao())
                .dia(request.getDia())
                .build();
    }

    public PagamentoResponse toResponse(Pagamento pagamento) {
        return PagamentoResponse.builder()
                .id(pagamento.getId())
                .participacaoId(pagamento.getParticipacaoId())
                .valorTotal(pagamento.getValorTotal())
                .valorPago(pagamento.getValorPago())
                .saldoDevedor(pagamento.getSaldoDevedor())
                .status(pagamento.getStatus())
                .lancamentos(pagamento.getLancamentos() != null ?
                    pagamento.getLancamentos().stream()
                        .map(this::toLancamentoResponse)
                        .collect(Collectors.toList()) : null)
                .build();
    }

    private LancamentoPagamentoResponse toLancamentoResponse(LancamentoPagamento lancamento) {
        return LancamentoPagamentoResponse.builder()
                .id(lancamento.getId())
                .valor(lancamento.getValor())
                .formaPagamento(lancamento.getFormaPagamento())
                .dataPagamento(lancamento.getDataPagamento())
                .observacao(lancamento.getObservacao())
                .dia(lancamento.getDia())
                .build();
    }
}
