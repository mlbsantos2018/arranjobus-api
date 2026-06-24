package com.transporte.application.service;

import com.transporte.domain.exception.RecursoNaoEncontradoException;
import com.transporte.domain.model.Evento;
import com.transporte.domain.model.LancamentoPagamento;
import com.transporte.domain.model.Pagamento;
import com.transporte.domain.model.Participacao;
import com.transporte.domain.enums.DiaEvento;
import com.transporte.domain.enums.StatusPagamento;
import com.transporte.domain.enums.TipoEvento;
import com.transporte.domain.ports.out.EventoRepositoryPort;
import com.transporte.domain.ports.out.ParticipacaoRepositoryPort;
import com.transporte.domain.ports.out.PagamentoRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PagamentoService {

    private final PagamentoRepositoryPort pagamentoRepository;
    private final ParticipacaoRepositoryPort participacaoRepository;
    private final EventoRepositoryPort eventoRepository;

    public Pagamento criar(Pagamento pagamento) {
        Participacao participacao = participacaoRepository.buscarPorId(pagamento.getParticipacaoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Participacao", pagamento.getParticipacaoId().toString()));

        Evento evento = eventoRepository.buscarPorId(participacao.getEventoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento", participacao.getEventoId().toString()));

        var valorTotal = calcularValorTotal(evento.getTipo(), evento.getValorPassagem(), participacao.getDias());

        pagamento.setEventoId(evento.getId());
        pagamento.setValorTotal(valorTotal);
        pagamento.setStatus(StatusPagamento.PENDENTE);
        pagamento.setValorPago(BigDecimal.ZERO);
        pagamento.setSaldoDevedor(valorTotal);
        pagamento.setLancamentos(new ArrayList<>());
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

    private BigDecimal calcularValorTotal(TipoEvento tipoEvento, BigDecimal valorPassagem, List<DiaEvento> dias) {
        if (tipoEvento == TipoEvento.ASSEMBLEIA) {
            return valorPassagem;
        }

        if (tipoEvento == TipoEvento.CONGRESSO) {
            var quantidadeDias = dias == null || dias.isEmpty() ? 1 : dias.size();
            var valorDiaria = valorPassagem.divide(BigDecimal.valueOf(3), 2, RoundingMode.HALF_UP);
            return valorDiaria.multiply(BigDecimal.valueOf(quantidadeDias));
        }

        return valorPassagem;
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
