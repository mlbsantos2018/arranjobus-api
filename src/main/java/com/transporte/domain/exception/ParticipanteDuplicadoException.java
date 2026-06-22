package com.transporte.domain.exception;

import java.util.UUID;

public class ParticipanteDuplicadoException extends DomainException {
    public ParticipanteDuplicadoException(UUID pessoaId, UUID eventoId) {
        super("Participante já registrado no evento. PessoaId: " + pessoaId + ", EventoId: " + eventoId);
    }
}
