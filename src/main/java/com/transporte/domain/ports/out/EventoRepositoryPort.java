package com.transporte.domain.ports.out;

import com.transporte.domain.enums.StatusEvento;
import com.transporte.domain.model.Evento;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventoRepositoryPort {
    Evento criar(Evento evento);

    Evento atualizar(Evento evento);

    void excluir(UUID id);

    Optional<Evento> buscarPorId(UUID id);

    List<Evento> listar();

    List<Evento> buscarPorStatus(StatusEvento status);
}
