package org.bertolo.exceptions;

public class MensagemNotFoundException extends RuntimeException {
    public MensagemNotFoundException(String message) {
        super(message);
    }
}
