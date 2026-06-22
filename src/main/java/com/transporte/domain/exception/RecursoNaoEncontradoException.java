package com.transporte.domain.exception;

public class RecursoNaoEncontradoException extends DomainException {
    public RecursoNaoEncontradoException(String recurso, String identificador) {
        super(recurso + " não encontrado: " + identificador);
    }
}
