package it.epicode.U2J_W4_D5_PROJECT.modules.exceptions;

public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(){}
    public AlreadyExistsException(String message) {
        super(message);
    }
}
