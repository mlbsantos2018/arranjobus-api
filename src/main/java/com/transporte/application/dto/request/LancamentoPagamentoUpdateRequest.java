package com.transporte.application.dto.request;

import com.transporte.domain.enums.FormaPagamento;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LancamentoPagamentoUpdateRequest {

    @DecimalMin(value = "0.01", message = "Valor do lançamento deve ser maior que zero")
    private BigDecimal valor;

    @NotNull(message = "Forma de pagamento é obrigatória")
    private FormaPagamento formaPagamento;

    private LocalDateTime dataPagamento;

    private String observacao;
}
