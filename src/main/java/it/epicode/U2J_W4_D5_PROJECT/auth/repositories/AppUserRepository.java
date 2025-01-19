package it.epicode.U2J_W4_D5_PROJECT.auth.repositories;


import it.epicode.U2J_W4_D5_PROJECT.auth.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);
    boolean existsByUsername(String username);
}
