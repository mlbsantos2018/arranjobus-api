package com.transporte.application.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CriancaColoResponse {

    private UUID id;
    private String nomeCompleto;
    private String documento;
    private LocalDate dataNascimento;
}
