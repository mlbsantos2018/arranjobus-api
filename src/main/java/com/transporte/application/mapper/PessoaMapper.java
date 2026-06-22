package com.transporte.application.mapper;

import com.transporte.application.dto.request.PessoaCreateRequest;
import com.transporte.application.dto.request.PessoaUpdateRequest;
import com.transporte.application.dto.response.PessoaResponse;
import com.transporte.domain.model.Pessoa;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class PessoaMapper {

    public Pessoa toPessoa(PessoaCreateRequest request) {
        return Pessoa.builder()
                .id(UUID.randomUUID())
                .nomeCompleto(request.getNomeCompleto())
                .tipoDocumento(request.getTipoDocumento())
                .numeroDocumento(request.getNumeroDocumento())
                .telefone(request.getTelefone())
                .observacaoCurta(request.getObservacaoCurta())
                .observacaoDetalhada(request.getObservacaoDetalhada())
                .criadoEm(LocalDateTime.now())
                .atualizadoEm(LocalDateTime.now())
                .build();
    }

    public Pessoa toPessoa(UUID id, PessoaUpdateRequest request, Pessoa pessoa) {
        pessoa.setNomeCompleto(request.getNomeCompleto());
        pessoa.setTelefone(request.getTelefone());
        pessoa.setObservacaoCurta(request.getObservacaoCurta());
        pessoa.setObservacaoDetalhada(request.getObservacaoDetalhada());
        pessoa.setAtualizadoEm(LocalDateTime.now());
        return pessoa;
    }

    public PessoaResponse toResponse(Pessoa pessoa) {
        return PessoaResponse.builder()
                .id(pessoa.getId())
                .nomeCompleto(pessoa.getNomeCompleto())
                .tipoDocumento(pessoa.getTipoDocumento())
                .numeroDocumento(pessoa.getNumeroDocumento())
                .telefone(pessoa.getTelefone())
                .observacaoCurta(pessoa.getObservacaoCurta())
                .observacaoDetalhada(pessoa.getObservacaoDetalhada())
                .criadoEm(pessoa.getCriadoEm())
                .atualizadoEm(pessoa.getAtualizadoEm())
                .build();
    }
}
