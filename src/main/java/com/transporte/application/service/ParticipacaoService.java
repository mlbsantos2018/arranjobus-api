package com.transporte.application.service;

import com.transporte.domain.exception.*;
import com.transporte.domain.model.Participacao;
import com.transporte.domain.model.Evento;
import com.transporte.domain.enums.DiaEvento;
import com.transporte.domain.enums.StatusEvento;
import com.transporte.domain.ports.out.ParticipacaoRepositoryPort;
import com.transporte.domain.ports.out.EventoRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ParticipacaoService {

    private final ParticipacaoRepositoryPort participacaoRepository;
    private final EventoRepositoryPort eventoRepository;

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

        // Validar vagas
        long totalParticipantes = participacaoRepository.buscarPorEventoId(evento.getId()).size();
        if (totalParticipantes >= evento.getVagasTotais()) {
            throw new VagasLotadasException(evento.getId());
        }

        return participacaoRepository.criar(participacao);
    }

    public void remover(UUID id) {
        participacaoRepository.buscarPorId(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Participacao", id.toString()));

        participacaoRepository.excluir(id);
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
