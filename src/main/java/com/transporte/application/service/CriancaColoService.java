package com.transporte.application.service;

import com.transporte.domain.exception.RecursoNaoEncontradoException;
import com.transporte.domain.model.CriancaColo;
import com.transporte.domain.ports.out.CriancaColoRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CriancaColoService {

    private final CriancaColoRepositoryPort criancaColoRepository;

    public CriancaColo criar(CriancaColo criancaColo) {
        return criancaColoRepository.criar(criancaColo);
    }

    public CriancaColo buscarPorId(UUID id) {
        return criancaColoRepository.buscarPorId(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Crianca Colo", id.toString()));
    }

    public CriancaColo buscarPorDocumento(String documento) {
        return criancaColoRepository.buscarPorDocumento(documento)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Crianca Colo", documento));
    }

    public List<CriancaColo> listar() {
        return criancaColoRepository.listar();
    }

    public void excluir(UUID id) {
        criancaColoRepository.buscarPorId(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Crianca Colo", id.toString()));
        criancaColoRepository.excluir(id);
    }

    public CriancaColo atualizar(UUID id, CriancaColo criancaColo) {
        criancaColoRepository.buscarPorId(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Crianca Colo", id.toString()));
        criancaColo.setId(id);
        return criancaColoRepository.atualizar(criancaColo);
    }
}
