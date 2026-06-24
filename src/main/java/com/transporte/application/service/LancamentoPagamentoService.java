package com.transporte.application.service;

import com.transporte.domain.exception.RecursoNaoEncontradoException;
import com.transporte.domain.model.LancamentoPagamento;
import com.transporte.domain.model.Pagamento;
import com.transporte.domain.enums.StatusPagamento;
import com.transporte.domain.ports.out.PagamentoRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class LancamentoPagamentoService {

    private final PagamentoRepositoryPort pagamentoRepository;

    public Pagamento atualizarLancamento(UUID pagamentoId, UUID lancamentoId, LancamentoPagamento lancamentoAtualizado) {
        Pagamento pagamento = pagamentoRepository.buscarPorId(pagamentoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pagamento", pagamentoId.toString()));

        var lancamentoExistente = pagamento.getLancamentos().stream()
                .filter(l -> l.getId().equals(lancamentoId))
                .findFirst()
                .orElseThrow(() -> new RecursoNaoEncontradoException("Lançamento de pagamento", lancamentoId.toString()));

        lancamentoExistente.setFormaPagamento(lancamentoAtualizado.getFormaPagamento());
        if (lancamentoAtualizado.getDataPagamento() != null) {
            lancamentoExistente.setDataPagamento(lancamentoAtualizado.getDataPagamento());
        }
        lancamentoExistente.setObservacao(lancamentoAtualizado.getObservacao());
        if (lancamentoAtualizado.getDia() != null) {
            lancamentoExistente.setDia(lancamentoAtualizado.getDia());
        }

        atualizarStatusPagamento(pagamento);
        return pagamentoRepository.atualizar(pagamento);
    }

    private void atualizarStatusPagamento(Pagamento pagamento) {
        BigDecimal totalPago = pagamento.getLancamentos().stream()
                .map(LancamentoPagamento::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        pagamento.setValorPago(totalPago);
        var saldo = pagamento.getValorTotal().subtract(totalPago);
        pagamento.setSaldoDevedor(saldo.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : saldo);

        if (pagamento.getValorPago().compareTo(pagamento.getValorTotal()) >= 0) {
            pagamento.setStatus(StatusPagamento.PAGO);
        } else if (pagamento.getValorPago().compareTo(BigDecimal.ZERO) > 0) {
            pagamento.setStatus(StatusPagamento.PARCIAL);
        } else {
            pagamento.setStatus(StatusPagamento.PENDENTE);
        }
    }
}
