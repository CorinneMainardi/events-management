package it.epicode.U2J_W4_D5_PROJECT.event;

import it.epicode.U2J_W4_D5_PROJECT.auth.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository <Event, Long> {
    List<Event> findByOrganizer(AppUser organizer);
    List<Event> findByAvailableSeatsGreaterThan(int availableSeats);
}
