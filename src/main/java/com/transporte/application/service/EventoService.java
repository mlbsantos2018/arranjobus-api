package com.transporte.application.service;

import com.transporte.domain.exception.RecursoNaoEncontradoException;
import com.transporte.domain.model.Evento;
import com.transporte.domain.enums.StatusEvento;
import com.transporte.domain.ports.out.EventoRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EventoService {

    private final EventoRepositoryPort eventoRepository;

    public Evento criar(Evento evento) {
        return eventoRepository.criar(evento);
    }

    public Evento atualizar(UUID id, Evento evento) {
        Evento existente = eventoRepository.buscarPorId(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento", id.toString()));
        
        evento.setId(id);
        return eventoRepository.atualizar(evento);
    }

    public void excluir(UUID id) {
        eventoRepository.buscarPorId(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento", id.toString()));
        
        eventoRepository.excluir(id);
    }

    public Evento buscarPorId(UUID id) {
        return eventoRepository.buscarPorId(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento", id.toString()));
    }

    public List<Evento> listar() {
        return eventoRepository.listar();
    }

    public Evento encerrar(UUID id) {
        Evento evento = buscarPorId(id);
        evento.setStatus(StatusEvento.ENCERRADO);
        return eventoRepository.atualizar(evento);
    }

    public Evento arquivar(UUID id) {
        Evento evento = buscarPorId(id);
        evento.setStatus(StatusEvento.ARQUIVADO);
        return eventoRepository.atualizar(evento);
    }
}
