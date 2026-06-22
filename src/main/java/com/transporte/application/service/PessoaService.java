package com.transporte.application.service;

import com.transporte.domain.exception.DocumentoDuplicadoException;
import com.transporte.domain.exception.RecursoNaoEncontradoException;
import com.transporte.domain.model.Pessoa;
import com.transporte.domain.ports.out.PessoaRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PessoaService {

    private final PessoaRepositoryPort pessoaRepository;

    public Pessoa criar(Pessoa pessoa) {
        if (pessoaRepository.existePorDocumento(pessoa.getNumeroDocumento())) {
            throw new DocumentoDuplicadoException(pessoa.getNumeroDocumento());
        }
        return pessoaRepository.criar(pessoa);
    }

    public Pessoa atualizar(UUID id, Pessoa pessoa) {
        Pessoa existente = pessoaRepository.buscarPorId(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pessoa", id.toString()));
        
        pessoa.setId(id);
        return pessoaRepository.atualizar(pessoa);
    }

    public void excluir(UUID id) {
        pessoaRepository.buscarPorId(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pessoa", id.toString()));
        
        pessoaRepository.excluir(id);
    }

    public Pessoa buscarPorId(UUID id) {
        return pessoaRepository.buscarPorId(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pessoa", id.toString()));
    }

    public List<Pessoa> pesquisar(String termo) {
        return pessoaRepository.pesquisar(termo);
    }

    public List<Pessoa> listar() {
        return pessoaRepository.listarTodas();
    }
}
