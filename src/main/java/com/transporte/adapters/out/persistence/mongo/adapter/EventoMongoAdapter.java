package com.transporte.adapters.out.persistence.mongo.adapter;

import com.transporte.adapters.out.persistence.mongo.mapper.EventoMongoMapper;
import com.transporte.adapters.out.persistence.mongo.repository.EventoMongoRepository;
import com.transporte.domain.enums.StatusEvento;
import com.transporte.domain.model.Evento;
import com.transporte.domain.ports.out.EventoRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class EventoMongoAdapter implements EventoRepositoryPort {

    private final EventoMongoRepository repository;
    private final EventoMongoMapper mapper;

    @Override
    public Evento criar(Evento evento) {
        if (evento.getId() == null) {
            evento.setId(UUID.randomUUID());
        }
        var document = mapper.toEventoDocument(evento);
        var saved = repository.save(document);
        return mapper.toEvento(saved);
    }

    @Override
    public Evento atualizar(Evento evento) {
        var document = mapper.toEventoDocument(evento);
        var updated = repository.save(document);
        return mapper.toEvento(updated);
    }

    @Override
    public void excluir(UUID id) {
        repository.deleteById(id.toString());
    }

    @Override
    public Optional<Evento> buscarPorId(UUID id) {
        return repository.findById(id.toString()).map(mapper::toEvento);
    }

    @Override
    public List<Evento> listar() {
        return repository.findAll().stream().map(mapper::toEvento).collect(Collectors.toList());
    }

    @Override
    public List<Evento> buscarPorStatus(StatusEvento status) {
        return repository.findByStatus(status).stream().map(mapper::toEvento).collect(Collectors.toList());
    }

}
