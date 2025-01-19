package it.epicode.U2J_W4_D5_PROJECT.modules.repositories;


import it.epicode.U2J_W4_D5_PROJECT.auth.entities.AppUser;
import it.epicode.U2J_W4_D5_PROJECT.modules.entities.Event;
import it.epicode.U2J_W4_D5_PROJECT.modules.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUser(AppUser user);
    List<Reservation> findByEvent(Event event);
    Optional<Reservation> findByUserAndEvent(AppUser user, Event event);
    boolean existsByUserAndEvent(AppUser user, Event event);
    void deleteByEvent(Event event);

}
