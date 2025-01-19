package it.epicode.U2J_W4_D5_PROJECT.auth.controllers;

import it.epicode.U2J_W4_D5_PROJECT.auth.dto.responses.AuthResponse;
import it.epicode.U2J_W4_D5_PROJECT.auth.enums.Role;
import it.epicode.U2J_W4_D5_PROJECT.auth.dto.requests.LoginRequest;
import it.epicode.U2J_W4_D5_PROJECT.auth.dto.requests.RegisterRequest;
import it.epicode.U2J_W4_D5_PROJECT.auth.services.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AppUserService appUserService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        appUserService.registerUser(
                registerRequest.getUsername(),
                registerRequest.getPassword(),
                Set.of(Role.ROLE_USER) // Assegna il ruolo di default
        );
        return ResponseEntity.status(HttpStatus.CREATED).body("Registrazione avvenuta con successo");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        String token = appUserService.authenticateUser(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(new AuthResponse(token));

    }
}
