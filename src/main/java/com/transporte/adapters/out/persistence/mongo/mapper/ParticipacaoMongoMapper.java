package com.transporte.adapters.out.persistence.mongo.mapper;

import com.transporte.adapters.out.persistence.mongo.document.ParticipacaoDocument;
import com.transporte.domain.model.Participacao;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class ParticipacaoMongoMapper {

    public ParticipacaoDocument toParticipacaoDocument(Participacao participacao) {
        return ParticipacaoDocument.builder()
                .id(participacao.getId().toString())
                .pessoaId(participacao.getPessoaId().toString())
                .eventoId(participacao.getEventoId().toString())
                .criancaColoId(participacao.getCriancaColoId() != null ? participacao.getCriancaColoId().toString() : null)
                .dias(participacao.getDias())
                .status(participacao.getStatus())
                .criadoEm(participacao.getCriadoEm() != null ? participacao.getCriadoEm() : LocalDateTime.now())
                .build();
    }

    public Participacao toParticipacao(ParticipacaoDocument document) {
        return Participacao.builder()
                .id(UUID.fromString(document.getId()))
                .pessoaId(UUID.fromString(document.getPessoaId()))
                .eventoId(UUID.fromString(document.getEventoId()))
                .criancaColoId(document.getCriancaColoId() != null ? UUID.fromString(document.getCriancaColoId()) : null)
                .dias(document.getDias())
                .status(document.getStatus())
                .criadoEm(document.getCriadoEm())
                .build();
    }
}
