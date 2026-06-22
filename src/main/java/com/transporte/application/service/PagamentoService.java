package com.transporte.application.service;

import com.transporte.domain.exception.RecursoNaoEncontradoException;
import com.transporte.domain.model.Pagamento;
import com.transporte.domain.model.LancamentoPagamento;
import com.transporte.domain.enums.StatusPagamento;
import com.transporte.domain.ports.out.PagamentoRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PagamentoService {

    private final PagamentoRepositoryPort pagamentoRepository;

    public Pagamento criar(Pagamento pagamento) {
        pagamento.setStatus(StatusPagamento.PENDENTE);
        pagamento.setValorPago(BigDecimal.ZERO);
        pagamento.setSaldoDevedor(pagamento.getValorTotal());
        return pagamentoRepository.criar(pagamento);
    }

    public Pagamento registrarParcela(UUID pagamentoId, LancamentoPagamento lancamento) {
        Pagamento pagamento = pagamentoRepository.buscarPorId(pagamentoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pagamento", pagamentoId.toString()));

        lancamento.setId(UUID.randomUUID());

        if (pagamento.getLancamentos() == null) {
            pagamento.setLancamentos(new ArrayList<>());
        }

        pagamento.getLancamentos().add(lancamento);

        atualizarStatusPagamento(pagamento);
        return pagamentoRepository.atualizar(pagamento);
    }

    public Pagamento buscarPorParticipacao(UUID participacaoId) {
        return pagamentoRepository.buscarPorParticipacaoId(participacaoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pagamento", participacaoId.toString()));
    }

    public Pagamento buscarPorId(UUID id) {
        return pagamentoRepository.buscarPorId(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pagamento", id.toString()));
    }

    public List<Pagamento> listar() {
        return pagamentoRepository.listar();
    }

    private void atualizarStatusPagamento(Pagamento pagamento) {
        BigDecimal totalPago = pagamento.getLancamentos().stream()
                .map(LancamentoPagamento::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        pagamento.setValorPago(totalPago);
        pagamento.setSaldoDevedor(pagamento.getValorTotal().subtract(totalPago));

        if (pagamento.getSaldoDevedor().compareTo(BigDecimal.ZERO) == 0) {
            pagamento.setStatus(StatusPagamento.PAGO);
        } else if (pagamento.getValorPago().compareTo(BigDecimal.ZERO) > 0) {
            pagamento.setStatus(StatusPagamento.PARCIAL);
        } else {
            pagamento.setStatus(StatusPagamento.PENDENTE);
        }
    }
}
