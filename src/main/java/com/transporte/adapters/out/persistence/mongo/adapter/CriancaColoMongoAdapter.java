package com.transporte.adapters.out.persistence.mongo.adapter;

import com.transporte.adapters.out.persistence.mongo.mapper.CriancaColoMongoMapper;
import com.transporte.adapters.out.persistence.mongo.repository.CriancaColoMongoRepository;
import com.transporte.domain.model.CriancaColo;
import com.transporte.domain.ports.out.CriancaColoRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CriancaColoMongoAdapter implements CriancaColoRepositoryPort {

    private final CriancaColoMongoRepository repository;
    private final CriancaColoMongoMapper mapper;

    @Override
    public CriancaColo criar(CriancaColo criancaColo) {
        if (criancaColo.getId() == null) {
            criancaColo.setId(UUID.randomUUID());
        }
        var document = mapper.toCriancaColoDocument(criancaColo);
        var saved = repository.save(document);
        return mapper.toCriancaColo(saved);
    }

    @Override
    public CriancaColo atualizar(CriancaColo criancaColo) {
        var document = mapper.toCriancaColoDocument(criancaColo);
        var updated = repository.save(document);
        return mapper.toCriancaColo(updated);
    }

    @Override
    public void excluir(UUID id) {
        repository.deleteById(id.toString());
    }

    @Override
    public Optional<CriancaColo> buscarPorId(UUID id) {
        return repository.findById(id.toString()).map(mapper::toCriancaColo);
    }

    @Override
    public List<CriancaColo> listar() {
        return repository.findAll().stream()
                .map(mapper::toCriancaColo)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CriancaColo> buscarPorDocumento(String documento) {
        return repository.findByDocumento(documento).map(mapper::toCriancaColo);
    }
}
