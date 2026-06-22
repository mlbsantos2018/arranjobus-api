package com.transporte.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CriancaColoCreateRequest {

    @NotBlank(message = "Nome completo é obrigatório")
    private String nomeCompleto;

    @NotBlank(message = "Documento é obrigatório")
    private String documento;

    @NotNull(message = "Data de nascimento é obrigatória")
    private LocalDate dataNascimento;
}
