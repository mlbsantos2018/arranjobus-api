package com.transporte.domain.model;

import com.transporte.domain.enums.TipoEvento;
import com.transporte.domain.enums.StatusEvento;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "id")
public class Evento {

    private UUID id;
    private String nome;
    private TipoEvento tipo;
    private BigDecimal valorPassagem;
    private Integer vagasTotais;
    private Integer limiteIdadeCriancaColo;
    private StatusEvento status;
    private List<String> temas;
    private LocalDateTime criadoEm;
}

