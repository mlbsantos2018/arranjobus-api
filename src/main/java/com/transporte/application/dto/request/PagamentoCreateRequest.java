package com.transporte.application.dto.request;

import com.transporte.domain.enums.FormaPagamento;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PagamentoCreateRequest {

    @NotNull(message = "ID da participação é obrigatório")
    private UUID participacaoId;

    @NotNull(message = "Valor total é obrigatório")
    @DecimalMin(value = "0.01", message = "Valor total deve ser maior que zero")
    private BigDecimal valorTotal;

    @NotNull(message = "Forma de pagamento é obrigatória")
    private FormaPagamento formaPagamento;

    private LocalDateTime dataPagamento;
    
    private String observacao;
}
