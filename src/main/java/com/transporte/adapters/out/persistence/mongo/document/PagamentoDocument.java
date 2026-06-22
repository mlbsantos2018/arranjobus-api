package com.transporte.adapters.out.persistence.mongo.document;

import com.transporte.domain.enums.StatusPagamento;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Document(collection = "payments")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PagamentoDocument {

    @Id
    private String id;

    private String participacaoId;

    private BigDecimal valorTotal;

    private BigDecimal valorPago;

    private BigDecimal saldoDevedor;

    private StatusPagamento status;

    private List<LancamentoPagamentoDocument> lancamentos;
}
