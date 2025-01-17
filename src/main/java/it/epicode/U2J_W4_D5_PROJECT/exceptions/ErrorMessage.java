package it.epicode.U2J_W4_D5_PROJECT.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorMessage  {
    private String message;
    private HttpStatus statusCode;
}