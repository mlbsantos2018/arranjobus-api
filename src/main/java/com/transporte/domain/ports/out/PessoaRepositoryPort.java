package com.transporte.domain.ports.out;

import com.transporte.domain.model.Pessoa;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PessoaRepositoryPort {
    Pessoa criar(Pessoa pessoa);

    Pessoa atualizar(Pessoa pessoa);

    void excluir(UUID id);

    Optional<Pessoa> buscarPorId(UUID id);

    Optional<Pessoa> buscarPorDocumento(String documento);

    List<Pessoa> pesquisar(String termo);

    List<Pessoa> listarTodas();

    boolean existePorDocumento(String documento);
}
