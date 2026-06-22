package com.transporte.domain.model;

import com.transporte.domain.enums.StatusParticipacao;
import com.transporte.domain.enums.DiaEvento;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "id")
public class Participacao {

    private UUID id;
    private UUID pessoaId;
    private UUID eventoId;
    private UUID criancaColoId;
    private List<DiaEvento> dias;
    private StatusParticipacao status;
    private LocalDateTime criadoEm;
}

