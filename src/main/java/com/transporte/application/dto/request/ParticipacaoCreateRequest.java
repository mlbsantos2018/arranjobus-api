package com.transporte.application.dto.request;

import com.transporte.domain.enums.DiaEvento;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ParticipacaoCreateRequest {

    @NotNull(message = "ID da pessoa é obrigatório")
    private UUID pessoaId;

    @NotNull(message = "ID do evento é obrigatório")
    private UUID eventoId;

    private UUID criancaColoId;

    @NotNull(message = "Dias são obrigatórios")
    private List<DiaEvento> dias;
}
