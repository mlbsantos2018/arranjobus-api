package com.transporte.domain.model;

import com.transporte.domain.enums.TipoDocumento;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "id")
public class Pessoa {

    private UUID id;
    private String nomeCompleto;
    private TipoDocumento tipoDocumento;
    private String numeroDocumento;
    private boolean criancaColo;
    private String telefone;
    private String observacaoCurta;
    private String observacaoDetalhada;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
}

