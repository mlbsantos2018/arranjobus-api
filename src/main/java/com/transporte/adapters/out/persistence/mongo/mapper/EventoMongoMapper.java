package com.transporte.adapters.out.persistence.mongo.mapper;

import com.transporte.adapters.out.persistence.mongo.document.EventoDocument;
import com.transporte.domain.model.Evento;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class EventoMongoMapper {

    public EventoDocument toEventoDocument(Evento evento) {
        return EventoDocument.builder()
                .id(evento.getId().toString())
                .nome(evento.getNome())
                .tipo(evento.getTipo())
                .valorPassagem(evento.getValorPassagem())
                .vagasTotais(evento.getVagasTotais())
                .limiteIdadeCriancaColo(evento.getLimiteIdadeCriancaColo())
                .status(evento.getStatus())
                .temas(evento.getTemas())
                .criadoEm(evento.getCriadoEm() != null ? evento.getCriadoEm() : LocalDateTime.now())
                .dataOcorrencia(evento.getDataOcorrencia())
                .diaAssembleia(evento.getDiaAssembleia())
                .build();
    }

    public Evento toEvento(EventoDocument document) {
        return Evento.builder()
                .id(UUID.fromString(document.getId()))
                .nome(document.getNome())
                .tipo(document.getTipo())
                .valorPassagem(document.getValorPassagem())
                .vagasTotais(document.getVagasTotais())
                .limiteIdadeCriancaColo(document.getLimiteIdadeCriancaColo())
                .status(document.getStatus())
                .temas(document.getTemas())
                .criadoEm(document.getCriadoEm())
                .dataOcorrencia(document.getDataOcorrencia())
                .diaAssembleia(document.getDiaAssembleia())
                .build();
    }
}
