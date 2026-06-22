package com.transporte.adapters.out.persistence.mongo.document;

import com.transporte.domain.enums.FormaPagamento;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LancamentoPagamentoDocument {

    private String id;

    private BigDecimal valor;

    private FormaPagamento formaPagamento;

    private LocalDateTime dataPagamento;

    private String observacao;
}
