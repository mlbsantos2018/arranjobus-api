package com.transporte.domain.ports.out;

import com.transporte.domain.model.CriancaColo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CriancaColoRepositoryPort {
    CriancaColo criar(CriancaColo criancaColo);

    CriancaColo atualizar(CriancaColo criancaColo);

    void excluir(UUID id);

    Optional<CriancaColo> buscarPorId(UUID id);

    List<CriancaColo> listar();

    Optional<CriancaColo> buscarPorDocumento(String documento);
}
