package com.transporte.application.dto.response;

import com.transporte.domain.enums.TipoEvento;
import com.transporte.domain.enums.StatusEvento;
import com.transporte.domain.enums.DiaEvento;
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
public class EventoDetailResponse {

    private UUID id;
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
    private BigDecimal valorTotalEsperado;
    
    // Campos consolidados
    private Integer totalParticipantes;
    private BigDecimal totalArrecadado;
    private Double percentualCapacidade;
    private Double percentualArrecadacao;
}
