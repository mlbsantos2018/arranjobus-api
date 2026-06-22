package com.transporte.domain.exception;

import java.util.UUID;

public class VagasLotadasException extends DomainException {
    public VagasLotadasException(UUID eventoId) {
        super("Vagas lotadas para este dia do evento. EventoId: " + eventoId);
    }
}
