package com.transporte.adapters.out.persistence.mongo.mapper;

import com.transporte.adapters.out.persistence.mongo.document.PagamentoDocument;
import com.transporte.adapters.out.persistence.mongo.document.LancamentoPagamentoDocument;
import com.transporte.domain.model.Pagamento;
import com.transporte.domain.model.LancamentoPagamento;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class PagamentoMongoMapper {

    public PagamentoDocument toPagamentoDocument(Pagamento pagamento) {
        return PagamentoDocument.builder()
                .id(pagamento.getId().toString())
                .eventoId(pagamento.getEventoId() != null ? pagamento.getEventoId().toString() : null)
                .participacaoId(pagamento.getParticipacaoId().toString())
                .valorTotal(pagamento.getValorTotal())
                .valorPago(pagamento.getValorPago())
                .saldoDevedor(pagamento.getSaldoDevedor())
                .status(pagamento.getStatus())
                .lancamentos(pagamento.getLancamentos() != null ? 
                    pagamento.getLancamentos().stream()
                        .map(this::toLancamentoDocument)
                        .collect(Collectors.toList()) : null)
                .build();
    }

    public Pagamento toPagamento(PagamentoDocument document) {
        return Pagamento.builder()
                .id(UUID.fromString(document.getId()))
                .eventoId(document.getEventoId() != null ? UUID.fromString(document.getEventoId()) : null)
                .participacaoId(UUID.fromString(document.getParticipacaoId()))
                .valorTotal(document.getValorTotal())
                .valorPago(document.getValorPago())
                .saldoDevedor(document.getSaldoDevedor())
                .status(document.getStatus())
                .lancamentos(document.getLancamentos() != null ?
                    document.getLancamentos().stream()
                        .map(this::toLancamento)
                        .collect(Collectors.toList()) : null)
                .build();
    }

    private LancamentoPagamentoDocument toLancamentoDocument(LancamentoPagamento lancamento) {
        return LancamentoPagamentoDocument.builder()
                .id(lancamento.getId().toString())
                .valor(lancamento.getValor())
                .formaPagamento(lancamento.getFormaPagamento())
                .dataPagamento(lancamento.getDataPagamento())
                .observacao(lancamento.getObservacao())
                .dia(lancamento.getDia())
                .build();
    }

    private LancamentoPagamento toLancamento(LancamentoPagamentoDocument document) {
        return LancamentoPagamento.builder()
                .id(UUID.fromString(document.getId()))
                .valor(document.getValor())
                .formaPagamento(document.getFormaPagamento())
                .dataPagamento(document.getDataPagamento())
                .observacao(document.getObservacao())
                .dia(document.getDia())
                .build();
    }
}
