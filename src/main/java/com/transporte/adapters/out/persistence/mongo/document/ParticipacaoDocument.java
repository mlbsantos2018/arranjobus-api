package com.transporte.adapters.out.persistence.mongo.document;

import com.transporte.domain.enums.StatusParticipacao;
import com.transporte.domain.enums.DiaEvento;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "participations")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ParticipacaoDocument {

    @Id
    private String id;

    private String pessoaId;

    private String eventoId;

    private String criancaColoId;

    private List<DiaEvento> dias;

    private StatusParticipacao status;

    private LocalDateTime criadoEm;
}
