package com.transporte.application.dto.response;

import com.transporte.domain.enums.StatusPagamento;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PagamentoResponse {

    private UUID id;
    private UUID eventoId;
    private UUID participacaoId;
    private BigDecimal valorTotal;
    private BigDecimal valorPago;
    private BigDecimal saldoDevedor;
    private StatusPagamento status;
    private List<LancamentoPagamentoResponse> lancamentos;
}
