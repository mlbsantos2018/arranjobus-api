package com.transporte.domain.model;

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
@EqualsAndHashCode(of = "id")
public class Pagamento {

    private UUID id;
    private UUID participacaoId;
    private BigDecimal valorTotal;
    private BigDecimal valorPago;
    private BigDecimal saldoDevedor;
    private StatusPagamento status;
    private List<LancamentoPagamento> lancamentos;
}

