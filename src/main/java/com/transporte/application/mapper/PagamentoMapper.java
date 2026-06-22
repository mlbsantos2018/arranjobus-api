package com.transporte.application.mapper;

import com.transporte.application.dto.response.PagamentoResponse;
import com.transporte.application.dto.response.LancamentoPagamentoResponse;
import com.transporte.domain.model.Pagamento;
import com.transporte.domain.model.LancamentoPagamento;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PagamentoMapper {

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
                .build();
    }
}
