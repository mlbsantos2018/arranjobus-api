package com.transporte.application.service;

import com.transporte.domain.exception.*;
import com.transporte.domain.model.Participacao;
import com.transporte.domain.model.Evento;
import com.transporte.domain.model.Pagamento;
import com.transporte.domain.enums.DiaEvento;
import com.transporte.domain.enums.StatusPagamento;
import com.transporte.domain.enums.StatusEvento;
import com.transporte.domain.enums.StatusParticipacao;
import com.transporte.domain.enums.TipoEvento;
import com.transporte.domain.ports.out.ParticipacaoRepositoryPort;
import com.transporte.domain.ports.out.EventoRepositoryPort;
import com.transporte.domain.ports.out.PagamentoRepositoryPort;
import com.transporte.application.service.PagamentoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ParticipacaoService {

    private final ParticipacaoRepositoryPort participacaoRepository;
    private final EventoRepositoryPort eventoRepository;
    private final PagamentoRepositoryPort pagamentoRepository;
    private final PagamentoService pagamentoService;

    public Participacao adicionar(Participacao participacao) {
        // Validar evento
        Evento evento = eventoRepository.buscarPorId(participacao.getEventoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento", participacao.getEventoId().toString()));

        // Validar se evento está ativo
        if (!evento.getStatus().equals(StatusEvento.ATIVO)) {
            throw new EventoEncerradoException(evento.getId());
        }

        // Recuperar participação cancelada anterior, se existir, para preservar histórico de pagamento.
        Optional<Participacao> participanteExistente = participacaoRepository.buscarPorPessoaEEvento(participacao.getPessoaId(), participacao.getEventoId());
        if (participanteExistente.isPresent()) {
            var participacaoAnterior = participanteExistente.get();
            if (participacaoAnterior.getStatus() == StatusParticipacao.CONFIRMADO) {
                throw new ParticipanteDuplicadoException(participacao.getPessoaId(), participacao.getEventoId());
            }

            // Reativar participação cancelada e manter o pagamento existente.
            participacaoAnterior.setCriancaColoId(participacao.getCriancaColoId());
            participacaoAnterior.setDias(participacao.getDias());
            participacaoAnterior.setStatus(StatusParticipacao.CONFIRMADO);

            validarVagasPorDia(participacaoAnterior, evento);

            var participacaoReativada = participacaoRepository.atualizar(participacaoAnterior);
            pagamentoRepository.buscarPorParticipacaoId(participacaoReativada.getId()).ifPresent(pagamento -> {
                var novoValorTotal = pagamentoService.calcularValorTotal(evento.getTipo(), evento.getValorPassagem(), participacaoReativada.getDias());
                if (pagamento.getValorTotal() == null || pagamento.getValorTotal().compareTo(novoValorTotal) != 0) {
                    pagamento.setValorTotal(novoValorTotal);
                    pagamento.setSaldoDevedor(novoValorTotal.subtract(pagamento.getValorPago()).max(java.math.BigDecimal.ZERO));
                    pagamentoService.atualizar(pagamento);
                }
            });
            return participacaoReativada;
        }

        if (evento.getTipo() == TipoEvento.ASSEMBLEIA) {
            if (evento.getDiaAssembleia() == null) {
                throw new IllegalArgumentException("Dia da assembleia não definido para este evento");
            }
            participacao.setDias(List.of(evento.getDiaAssembleia()));
        }

        validarVagasPorDia(participacao, evento);

        Participacao created = participacaoRepository.criar(participacao);
        if (created.getCriancaColoId() == null) {
            try {
                var pagamento = Pagamento.builder()
                        .id(UUID.randomUUID())
                        .participacaoId(created.getId())
                        .build();
                pagamentoService.criar(pagamento);
            } catch (RuntimeException ex) {
                participacaoRepository.excluir(created.getId());
                throw ex;
            }
        }
        return created;
    }

    private void validarVagasPorDia(Participacao participacao, Evento evento) {
        if (participacao.getCriancaColoId() == null) {
            var dias = participacao.getDias();
            if (dias != null) {
                for (DiaEvento dia : dias) {
                    long participantesNoDia = participacaoRepository.buscarPorEventoIdEDia(evento.getId(), dia).stream()
                            .filter(p -> p.getStatus() == StatusParticipacao.CONFIRMADO)
                            .filter(p -> p.getCriancaColoId() == null)
                            .count();
                    if (participantesNoDia >= evento.getVagasTotais()) {
                        throw new VagasLotadasException(evento.getId());
                    }
                }
            }
        }
    }

    public void remover(UUID id, boolean keepPayment) {
        var participacao = participacaoRepository.buscarPorId(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Participacao", id.toString()));

        if (!keepPayment) {
            pagamentoRepository.buscarPorParticipacaoId(id).ifPresent(pagamento -> pagamentoRepository.excluir(pagamento.getId()));
            participacaoRepository.excluir(participacao.getId());
            return;
        }

        participacao.setStatus(StatusParticipacao.CANCELADO);
        participacaoRepository.atualizar(participacao);
    }

    public List<Participacao> listarPorEvento(UUID eventoId) {
        return participacaoRepository.buscarPorEventoId(eventoId).stream()
                .filter(participacao -> participacao.getStatus() == StatusParticipacao.CONFIRMADO)
                .toList();
    }

    public List<Participacao> listarPorEventoEDia(
            UUID eventoId,
            DiaEvento dia) {
        return participacaoRepository.buscarPorEventoIdEDia(
                eventoId,
                dia).stream()
                .filter(participacao -> participacao.getStatus() == StatusParticipacao.CONFIRMADO)
                .toList();
    }

    public Participacao buscarPorId(UUID id) {
        return participacaoRepository.buscarPorId(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Participacao", id.toString()));
    }
}
