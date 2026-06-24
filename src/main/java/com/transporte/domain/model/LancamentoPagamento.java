package com.transporte.domain.model;

import com.transporte.domain.enums.DiaEvento;
import com.transporte.domain.enums.FormaPagamento;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "id")
public class LancamentoPagamento {

    private UUID id;
    private BigDecimal valor;
    private FormaPagamento formaPagamento;
    private LocalDateTime dataPagamento;
    private String observacao;
    private DiaEvento dia;
}

