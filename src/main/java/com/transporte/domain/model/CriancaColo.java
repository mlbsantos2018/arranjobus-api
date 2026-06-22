package com.transporte.domain.model;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "id")
public class CriancaColo {

    private UUID id;
    private String nomeCompleto;
    private String documento;
    private LocalDate dataNascimento;
}

