package com.transporte.application.dto.request;

import com.transporte.domain.enums.TipoEvento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EventoCreateRequest {

    @NotBlank(message = "Nome do evento é obrigatório")
    private String nome;

    @NotNull(message = "Tipo de evento é obrigatório")
    private TipoEvento tipo;

    @NotNull(message = "Valor da passagem é obrigatório")
    @DecimalMin(value = "0.0")
    private BigDecimal valorPassagem;

    @NotNull(message = "Vagas totais é obrigatório")
    @Min(value = 1, message = "Deve ter no mínimo 1 vaga")
    private Integer vagasTotais;

    @Min(value = 0, message = "Limite de idade não pode ser negativo")
    private Integer limiteIdadeCriancaColo;

    private List<String> temas;
}
