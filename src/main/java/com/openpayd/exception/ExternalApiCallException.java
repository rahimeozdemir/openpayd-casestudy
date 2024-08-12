package com.openpayd.exception;

public class ExternalApiCallException extends RuntimeException {
    public ExternalApiCallException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExternalApiCallException(String message) {
        super(message);
    }
}
