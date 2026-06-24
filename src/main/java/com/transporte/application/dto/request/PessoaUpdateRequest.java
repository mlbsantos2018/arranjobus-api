package com.transporte.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PessoaUpdateRequest {

    @NotBlank(message = "Nome completo é obrigatório")
    private String nomeCompleto;

    private Boolean criancaColo;

    private String telefone;

    private String observacaoCurta;

    private String observacaoDetalhada;
}
