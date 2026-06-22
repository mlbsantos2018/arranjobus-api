package com.transporte.adapters.out.persistence.mongo.mapper;

import com.transporte.adapters.out.persistence.mongo.document.CriancaColoDocument;
import com.transporte.domain.model.CriancaColo;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CriancaColoMongoMapper {

    public CriancaColoDocument toCriancaColoDocument(CriancaColo criancaColo) {
        return CriancaColoDocument.builder()
                .id(criancaColo.getId().toString())
                .nomeCompleto(criancaColo.getNomeCompleto())
                .documento(criancaColo.getDocumento())
                .dataNascimento(criancaColo.getDataNascimento())
                .build();
    }

    public CriancaColo toCriancaColo(CriancaColoDocument document) {
        return CriancaColo.builder()
                .id(UUID.fromString(document.getId()))
                .nomeCompleto(document.getNomeCompleto())
                .documento(document.getDocumento())
                .dataNascimento(document.getDataNascimento())
                .build();
    }
}
