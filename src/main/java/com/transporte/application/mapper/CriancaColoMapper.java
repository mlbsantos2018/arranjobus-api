package com.transporte.application.mapper;

import com.transporte.application.dto.request.CriancaColoCreateRequest;
import com.transporte.application.dto.response.CriancaColoResponse;
import com.transporte.domain.model.CriancaColo;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CriancaColoMapper {

    public CriancaColo toCriancaColo(CriancaColoCreateRequest request) {
        return CriancaColo.builder()
                .id(UUID.randomUUID())
                .nomeCompleto(request.getNomeCompleto())
                .documento(request.getDocumento())
                .dataNascimento(request.getDataNascimento())
                .build();
    }

    public CriancaColoResponse toResponse(CriancaColo criancaColo) {
        return CriancaColoResponse.builder()
                .id(criancaColo.getId())
                .nomeCompleto(criancaColo.getNomeCompleto())
                .documento(criancaColo.getDocumento())
                .dataNascimento(criancaColo.getDataNascimento())
                .build();
    }
}
