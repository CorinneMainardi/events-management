package it.epicode.U2J_W4_D5_PROJECT.auth.runners;

import it.epicode.U2J_W4_D5_PROJECT.auth.entities.AppUser;
import it.epicode.U2J_W4_D5_PROJECT.auth.enums.Role;
import it.epicode.U2J_W4_D5_PROJECT.auth.services.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
public class AuthRunner implements ApplicationRunner {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Creazione dell'utente admin se non esiste
        Optional<AppUser> adminUser = appUserService.findByUsername("admin");
        if (adminUser.isEmpty()) {
            appUserService.registerUser("admin3", "adminpwd", Set.of(Role.ROLE_ADMIN));
        }

        // Creazione dell'utente event organiser se non esiste

        Optional<AppUser> eventOrganiserUser = appUserService.findByUsername("organiser");
        if (eventOrganiserUser.isEmpty()) {
            appUserService.registerUser("eventOrganiser3", "eventOrganiserpwd", Set.of(Role.ROLE_ORGANISER));
        }

        // Creazione dell'utente user se non esiste
        Optional<AppUser> normalUser = appUserService.findByUsername("user");
        if (normalUser.isEmpty()) {
            appUserService.registerUser("user3", "userpwd", Set.of(Role.ROLE_USER));
        }
    }
}
