package com.neves_eduardo.core_engineering.tema8.exceptions;

public class InvalidLoanRegistryException extends RuntimeException {
    public InvalidLoanRegistryException(String errorMessage) {
        super(errorMessage);
    }
}
