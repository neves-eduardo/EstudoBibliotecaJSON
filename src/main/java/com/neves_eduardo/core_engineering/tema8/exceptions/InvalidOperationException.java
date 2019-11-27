package com.neves_eduardo.core_engineering.tema8.exceptions;

public class InvalidOperationException extends RuntimeException{
    public InvalidOperationException(String errorMessage) {
        super (errorMessage);
    }
}
