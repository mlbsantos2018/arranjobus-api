package com.transporte.application.service;

import com.transporte.domain.exception.*;
import com.transporte.domain.model.Participacao;
import com.transporte.domain.model.Evento;
import com.transporte.domain.enums.DiaEvento;
import com.transporte.domain.enums.StatusEvento;
import com.transporte.domain.enums.TipoEvento;
import com.transporte.domain.ports.out.ParticipacaoRepositoryPort;
import com.transporte.domain.ports.out.EventoRepositoryPort;
import com.transporte.domain.ports.out.PagamentoRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ParticipacaoService {

    private final ParticipacaoRepositoryPort participacaoRepository;
    private final EventoRepositoryPort eventoRepository;
    private final PagamentoRepositoryPort pagamentoRepository;

    public Participacao adicionar(Participacao participacao) {
        // Validar evento
        Evento evento = eventoRepository.buscarPorId(participacao.getEventoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento", participacao.getEventoId().toString()));

        // Validar se evento está ativo
        if (!evento.getStatus().equals(StatusEvento.ATIVO)) {
            throw new EventoEncerradoException(evento.getId());
        }

        // Validar duplicidade
        if (participacaoRepository.existePorPessoaEEvento(participacao.getPessoaId(), participacao.getEventoId())) {
            throw new ParticipanteDuplicadoException(participacao.getPessoaId(), participacao.getEventoId());
        }

        if (evento.getTipo() == TipoEvento.ASSEMBLEIA) {
            if (evento.getDiaAssembleia() == null) {
                throw new IllegalArgumentException("Dia da assembleia não definido para este evento");
            }
            participacao.setDias(List.of(evento.getDiaAssembleia()));
        }

        // Validar vagas por dia
        if (participacao.getCriancaColoId() == null) {
            var dias = participacao.getDias();
            if (dias != null) {
                for (DiaEvento dia : dias) {
                    long participantesNoDia = participacaoRepository.buscarPorEventoIdEDia(evento.getId(), dia).stream()
                            .filter(p -> p.getCriancaColoId() == null)
                            .count();
                    if (participantesNoDia >= evento.getVagasTotais()) {
                        throw new VagasLotadasException(evento.getId());
                    }
                }
            }
        }

        return participacaoRepository.criar(participacao);
    }

    public void remover(UUID id, boolean keepPayment) {
        var participacao = participacaoRepository.buscarPorId(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Participacao", id.toString()));

        if (!keepPayment) {
            pagamentoRepository.buscarPorParticipacaoId(id).ifPresent(pagamento -> pagamentoRepository.excluir(pagamento.getId()));
        }

        participacaoRepository.excluir(participacao.getId());
    }

    public List<Participacao> listarPorEvento(UUID eventoId) {
        return participacaoRepository.buscarPorEventoId(eventoId);
    }

    public List<Participacao> listarPorEventoEDia(
            UUID eventoId,
            DiaEvento dia) {
        return participacaoRepository.buscarPorEventoIdEDia(
                eventoId,
                dia);
    }

    public Participacao buscarPorId(UUID id) {
        return participacaoRepository.buscarPorId(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Participacao", id.toString()));
    }
}
