package com.transporte.adapters.out.persistence.mongo.adapter;

import com.transporte.adapters.out.persistence.mongo.document.PessoaDocument;
import com.transporte.adapters.out.persistence.mongo.mapper.PessoaMongoMapper;
import com.transporte.adapters.out.persistence.mongo.repository.PessoaMongoRepository;
import com.transporte.domain.model.Pessoa;
import com.transporte.domain.ports.out.PessoaRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class PessoaMongoAdapter implements PessoaRepositoryPort {

    private final PessoaMongoRepository repository;
    private final PessoaMongoMapper mapper;

    @Override
    public Pessoa criar(Pessoa pessoa) {
        if (pessoa.getId() == null) {
            pessoa.setId(UUID.randomUUID());
        }
        PessoaDocument document = mapper.toPessoaDocument(pessoa);
        PessoaDocument saved = repository.save(document);
        return mapper.toPessoa(saved);
    }

    @Override
    public Pessoa atualizar(Pessoa pessoa) {
        PessoaDocument document = mapper.toPessoaDocument(pessoa);
        PessoaDocument updated = repository.save(document);
        return mapper.toPessoa(updated);
    }

    @Override
    public void excluir(UUID id) {
        repository.deleteById(id.toString());
    }

    @Override
    public Optional<Pessoa> buscarPorId(UUID id) {
        return repository.findById(id.toString()).map(mapper::toPessoa);
    }

    @Override
    public Optional<Pessoa> buscarPorDocumento(String documento) {
        return repository.findByNumeroDocumento(documento).map(mapper::toPessoa);
    }

    @Override
    public List<Pessoa> pesquisar(String termo) {
        String regex = ".*" + Pattern.quote(termo) + ".*";
        return repository.searchByText(regex).stream()
                .map(mapper::toPessoa)
                .collect(Collectors.toList());
    }

    @Override
    public List<Pessoa> listarTodas() {
        return repository.findAll().stream()
                .map(mapper::toPessoa)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existePorDocumento(String documento) {
        return repository.existsByNumeroDocumento(documento);
    }
}
