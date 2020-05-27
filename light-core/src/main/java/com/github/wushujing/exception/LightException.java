package com.github.wushujing.exception;

public class LightException extends RuntimeException {
    private static final long serialVersionUID = 3813214408155971104L;

    public LightException() {
    }

    public LightException(String message) {
        super(message);
    }

    public LightException(String message, Throwable cause) {
        super(message, cause);
    }

    public LightException(Throwable cause) {
        super(cause);
    }

    public LightException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
