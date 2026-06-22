package com.transporte.adapters.out.persistence.mongo.mapper;

import com.transporte.adapters.out.persistence.mongo.document.PessoaDocument;
import com.transporte.domain.model.Pessoa;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class PessoaMongoMapper {

    public PessoaDocument toPessoaDocument(Pessoa pessoa) {
        return PessoaDocument.builder()
                .id(pessoa.getId().toString())
                .nomeCompleto(pessoa.getNomeCompleto())
                .tipoDocumento(pessoa.getTipoDocumento())
                .numeroDocumento(pessoa.getNumeroDocumento())
                .telefone(pessoa.getTelefone())
                .observacaoCurta(pessoa.getObservacaoCurta())
                .observacaoDetalhada(pessoa.getObservacaoDetalhada())
                .criadoEm(pessoa.getCriadoEm() != null ? pessoa.getCriadoEm() : LocalDateTime.now())
                .atualizadoEm(LocalDateTime.now())
                .build();
    }

    public Pessoa toPessoa(PessoaDocument document) {
        return Pessoa.builder()
                .id(UUID.fromString(document.getId()))
                .nomeCompleto(document.getNomeCompleto())
                .tipoDocumento(document.getTipoDocumento())
                .numeroDocumento(document.getNumeroDocumento())
                .telefone(document.getTelefone())
                .observacaoCurta(document.getObservacaoCurta())
                .observacaoDetalhada(document.getObservacaoDetalhada())
                .criadoEm(document.getCriadoEm())
                .atualizadoEm(document.getAtualizadoEm())
                .build();
    }
}
