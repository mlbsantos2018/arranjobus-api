package com.transporte.adapters.out.persistence.mongo.adapter;

import com.transporte.adapters.out.persistence.mongo.mapper.PagamentoMongoMapper;
import com.transporte.adapters.out.persistence.mongo.repository.PagamentoMongoRepository;
import com.transporte.domain.model.Pagamento;
import com.transporte.domain.ports.out.PagamentoRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class PagamentoMongoAdapter implements PagamentoRepositoryPort {

    private final PagamentoMongoRepository repository;
    private final PagamentoMongoMapper mapper;

    @Override
    public Pagamento criar(Pagamento pagamento) {
        if (pagamento.getId() == null) {
            pagamento.setId(UUID.randomUUID());
        }
        var document = mapper.toPagamentoDocument(pagamento);
        var saved = repository.save(document);
        return mapper.toPagamento(saved);
    }

    @Override
    public Pagamento atualizar(Pagamento pagamento) {
        var document = mapper.toPagamentoDocument(pagamento);
        var updated = repository.save(document);
        return mapper.toPagamento(updated);
    }

    @Override
    public void excluir(UUID id) {
        repository.deleteById(id.toString());
    }

    @Override
    public Optional<Pagamento> buscarPorId(UUID id) {
        return repository.findById(id.toString()).map(mapper::toPagamento);
    }

    @Override
    public Optional<Pagamento> buscarPorParticipacaoId(UUID participacaoId) {
        return repository.findByParticipacaoId(participacaoId.toString()).map(mapper::toPagamento);
    }

    @Override
    public List<Pagamento> buscarPorPessoaId(UUID pessoaId) {
        // Implementar lógica de busca por pessoa se necessário
        return List.of();
    }

    @Override
    public List<Pagamento> listar() {
        return repository.findAll().stream()
                .map(mapper::toPagamento)
                .collect(Collectors.toList());
    }
}
