package com.neves_eduardo.core_engineering.tema8.exceptions;

public class DataAccessException extends RuntimeException {
    public DataAccessException(String errorMessage) {
        super(errorMessage);
    }

}
