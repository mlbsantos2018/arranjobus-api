package com.transporte.adapters.out.persistence.mongo.document;

import com.transporte.domain.enums.TipoEvento;
import com.transporte.domain.enums.StatusEvento;
import com.transporte.domain.enums.DiaEvento;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "events")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EventoDocument {

    @Id
    private String id;

    private String nome;

    private TipoEvento tipo;

    private BigDecimal valorPassagem;

    private Integer vagasTotais;

    private Integer limiteIdadeCriancaColo;

    private StatusEvento status;

    private List<String> temas;

    private LocalDateTime criadoEm;

    private LocalDateTime dataOcorrencia;

    private DiaEvento diaAssembleia;  // Para eventos tipo ASSEMBLEIA
}
