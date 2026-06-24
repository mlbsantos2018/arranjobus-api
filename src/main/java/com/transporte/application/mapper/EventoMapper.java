package com.transporte.application.mapper;

import com.transporte.application.dto.request.EventoCreateRequest;
import com.transporte.application.dto.response.EventoResponse;
import com.transporte.domain.model.Evento;
import com.transporte.domain.enums.StatusEvento;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class EventoMapper {

    public Evento toEvento(EventoCreateRequest request) {
        return Evento.builder()
                .id(UUID.randomUUID())
                .nome(request.getNome())
                .tipo(request.getTipo())
                .valorPassagem(request.getValorPassagem())
                .vagasTotais(request.getVagasTotais())
                .limiteIdadeCriancaColo(request.getLimiteIdadeCriancaColo())
                .status(StatusEvento.ATIVO)
                .temas(request.getTemas())
                .criadoEm(LocalDateTime.now())
                .dataOcorrencia(request.getDataOcorrencia())
                .diaAssembleia(request.getDiaAssembleia())
                .build();
    }

    public EventoResponse toResponse(Evento evento) {
        return EventoResponse.builder()
                .id(evento.getId())
                .nome(evento.getNome())
                .tipo(evento.getTipo())
                .valorPassagem(evento.getValorPassagem())
                .vagasTotais(evento.getVagasTotais())
                .limiteIdadeCriancaColo(evento.getLimiteIdadeCriancaColo())
                .status(evento.getStatus())
                .temas(evento.getTemas())
                .criadoEm(evento.getCriadoEm())
                .dataOcorrencia(evento.getDataOcorrencia())
                .diaAssembleia(evento.getDiaAssembleia())
                .build();
    }
}
