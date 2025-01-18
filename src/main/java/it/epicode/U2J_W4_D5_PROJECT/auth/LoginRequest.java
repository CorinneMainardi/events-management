package it.epicode.U2J_W4_D5_PROJECT.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "the field 'USERNAME' cannot be blank")
    private String username;
    @NotBlank (message = "the field 'PASSWORD' cannot be blank")
    private String password;
}
