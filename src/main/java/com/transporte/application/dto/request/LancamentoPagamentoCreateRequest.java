package com.transporte.application.dto.request;

import com.transporte.domain.enums.DiaEvento;
import com.transporte.domain.enums.FormaPagamento;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LancamentoPagamentoCreateRequest {

    @NotNull(message = "Valor do lançamento é obrigatório")
    @DecimalMin(value = "0.01", message = "Valor do lançamento deve ser maior que zero")
    private BigDecimal valor;

    @NotNull(message = "Forma de pagamento é obrigatória")
    private FormaPagamento formaPagamento;

    private LocalDateTime dataPagamento;

    private String observacao;

    private DiaEvento dia;
}
