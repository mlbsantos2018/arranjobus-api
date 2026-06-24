package com.transporte.adapters.out.persistence.mongo.adapter;

import com.transporte.adapters.out.persistence.mongo.mapper.ParticipacaoMongoMapper;
import com.transporte.adapters.out.persistence.mongo.repository.ParticipacaoMongoRepository;
import com.transporte.domain.model.Participacao;
import com.transporte.domain.ports.out.ParticipacaoRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import com.transporte.domain.enums.DiaEvento;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ParticipacaoMongoAdapter implements ParticipacaoRepositoryPort {

    private final ParticipacaoMongoRepository repository;
    private final ParticipacaoMongoMapper mapper;

    @Override
    public Participacao criar(Participacao participacao) {
        if (participacao.getId() == null) {
            participacao.setId(UUID.randomUUID());
        }
        var document = mapper.toParticipacaoDocument(participacao);
        var saved = repository.save(document);
        return mapper.toParticipacao(saved);
    }

    @Override
    public Participacao atualizar(Participacao participacao) {
        var document = mapper.toParticipacaoDocument(participacao);
        var updated = repository.save(document);
        return mapper.toParticipacao(updated);
    }

    @Override
    public void excluir(UUID id) {
        repository.deleteById(id.toString());
    }

    @Override
    public Optional<Participacao> buscarPorId(UUID id) {
        return repository.findById(id.toString()).map(mapper::toParticipacao);
    }

    @Override
    public List<Participacao> buscarPorEventoId(UUID eventoId) {
        return repository.findByEventoId(eventoId.toString()).stream()
                .map(mapper::toParticipacao)
                .collect(Collectors.toList());
    }

    @Override
    public List<Participacao> buscarPorPessoaId(UUID pessoaId) {
        return repository.findByPessoaId(pessoaId.toString()).stream()
                .map(mapper::toParticipacao)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Participacao> buscarPorPessoaEEvento(UUID pessoaId, UUID eventoId) {
        return repository.findByPessoaIdAndEventoId(pessoaId.toString(), eventoId.toString())
                .map(mapper::toParticipacao);
    }

    @Override
    public boolean existePorPessoaEEvento(UUID pessoaId, UUID eventoId) {
        return repository.existsByPessoaIdAndEventoId(pessoaId.toString(), eventoId.toString());
    }

    @Override
    public List<Participacao> buscarPorEventoIdEDia(
            UUID eventoId,
            DiaEvento dia) {
        return repository
                .findByEventoIdAndDiasContaining(
                        eventoId.toString(),
                        dia)
                .stream()
                .map(mapper::toParticipacao)
                .collect(Collectors.toList());
    }
}
