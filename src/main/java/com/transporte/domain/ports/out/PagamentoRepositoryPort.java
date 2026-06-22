package com.transporte.domain.ports.out;

import com.transporte.domain.model.Pagamento;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PagamentoRepositoryPort {
    Pagamento criar(Pagamento pagamento);

    Pagamento atualizar(Pagamento pagamento);

    void excluir(UUID id);

    Optional<Pagamento> buscarPorId(UUID id);

    Optional<Pagamento> buscarPorParticipacaoId(UUID participacaoId);

    List<Pagamento> buscarPorPessoaId(UUID pessoaId);

    List<Pagamento> listar();
}
