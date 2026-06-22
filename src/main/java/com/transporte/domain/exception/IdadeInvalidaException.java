package com.transporte.domain.exception;

public class IdadeInvalidaException extends DomainException {
    public IdadeInvalidaException(int idade, int limite) {
        super("Criança excede o limite de idade. Idade: " + idade + ", Limite: " + limite);
    }
}
