package com.transporte.domain.ports.out;

import com.transporte.domain.model.Participacao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.transporte.domain.enums.DiaEvento;

public interface ParticipacaoRepositoryPort {
    Participacao criar(Participacao participacao);

    Participacao atualizar(Participacao participacao);

    void excluir(UUID id);

    Optional<Participacao> buscarPorId(UUID id);

    List<Participacao> buscarPorEventoId(UUID eventoId);

    List<Participacao> buscarPorPessoaId(UUID pessoaId);

    Optional<Participacao> buscarPorPessoaEEvento(UUID pessoaId, UUID eventoId);

    boolean existePorPessoaEEvento(UUID pessoaId, UUID eventoId);

    List<Participacao> buscarPorEventoIdEDia(
            UUID eventoId,
            DiaEvento dia);
}
