package it.epicode.U2J_W4_D5_PROJECT.modules.repositories;

import it.epicode.U2J_W4_D5_PROJECT.auth.entities.AppUser;
import it.epicode.U2J_W4_D5_PROJECT.modules.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository <Event, Long> {
    List<Event> findByOrganizer(AppUser organizer);
    List<Event> findByAvailableSeatsGreaterThan(int availableSeats);
}
