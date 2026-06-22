package com.transporte.domain.exception;

import java.util.UUID;

public class EventoEncerradoException extends DomainException {
    public EventoEncerradoException(UUID eventoId) {
        super("Evento já foi encerrado. EventoId: " + eventoId);
    }
}
