package it.epicode.U2J_W4_D5_PROJECT.modules.exceptions;

public class InternalServerErrorException extends RuntimeException {
    public InternalServerErrorException(String message) {
        super(message);
    }
}
