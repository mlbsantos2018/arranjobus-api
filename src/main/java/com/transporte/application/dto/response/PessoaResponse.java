package com.transporte.application.dto.response;

import com.transporte.domain.enums.TipoDocumento;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PessoaResponse {

    private UUID id;
    private String nomeCompleto;
    private TipoDocumento tipoDocumento;
    private String numeroDocumento;
    private String telefone;
    private String observacaoCurta;
    private String observacaoDetalhada;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
}
