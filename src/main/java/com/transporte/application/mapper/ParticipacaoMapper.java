package com.transporte.application.mapper;

import com.transporte.application.dto.request.ParticipacaoCreateRequest;
import com.transporte.application.dto.response.ParticipacaoResponse;
import com.transporte.domain.enums.StatusPagamento;
import com.transporte.domain.model.Participacao;
import com.transporte.domain.enums.StatusParticipacao;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class ParticipacaoMapper {

    public Participacao toParticipacao(ParticipacaoCreateRequest request) {
        return Participacao.builder()
                .id(UUID.randomUUID())
                .pessoaId(request.getPessoaId())
                .eventoId(request.getEventoId())
                .criancaColoId(request.getCriancaColoId())
                .dias(request.getDias())
                .status(StatusParticipacao.CONFIRMADO)
                .criadoEm(LocalDateTime.now())
                .build();
    }

    public ParticipacaoResponse toResponse(Participacao participacao) {
        return ParticipacaoResponse.builder()
                .id(participacao.getId())
                .pessoaId(participacao.getPessoaId())
                .eventoId(participacao.getEventoId())
                .criancaColoId(participacao.getCriancaColoId())
                .dias(participacao.getDias())
                .status(participacao.getStatus())
                .paymentStatus(StatusPagamento.PENDENTE)
                .criadoEm(participacao.getCriadoEm())
                .build();
    }

    public ParticipacaoResponse toResponse(Participacao participacao, StatusPagamento paymentStatus) {
        return ParticipacaoResponse.builder()
                .id(participacao.getId())
                .pessoaId(participacao.getPessoaId())
                .eventoId(participacao.getEventoId())
                .criancaColoId(participacao.getCriancaColoId())
                .dias(participacao.getDias())
                .status(participacao.getStatus())
                .paymentStatus(paymentStatus)
                .criadoEm(participacao.getCriadoEm())
                .build();
    }
}
