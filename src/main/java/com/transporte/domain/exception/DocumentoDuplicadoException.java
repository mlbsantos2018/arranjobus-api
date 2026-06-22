package com.transporte.domain.exception;

public class DocumentoDuplicadoException extends DomainException {
    public DocumentoDuplicadoException(String documento) {
        super("Documento já cadastrado: " + documento);
    }
}
