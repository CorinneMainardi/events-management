package it.epicode.U2J_W4_D5_PROJECT.auth.dto.requests;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
}
