package com.transporte.application.dto.request;

import com.transporte.domain.enums.TipoDocumento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PessoaCreateRequest {

    @NotBlank(message = "Nome completo é obrigatório")
    private String nomeCompleto;

    @NotNull(message = "Tipo de documento é obrigatório")
    private TipoDocumento tipoDocumento;

    @NotBlank(message = "Número do documento é obrigatório")
    private String numeroDocumento;

    private String telefone;

    private String observacaoCurta;

    private String observacaoDetalhada;
}
