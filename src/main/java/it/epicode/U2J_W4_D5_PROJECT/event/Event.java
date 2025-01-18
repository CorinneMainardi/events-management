package it.epicode.U2J_W4_D5_PROJECT.event;

import it.epicode.U2J_W4_D5_PROJECT.auth.AppUser;
import it.epicode.U2J_W4_D5_PROJECT.auth.Role;
import it.epicode.U2J_W4_D5_PROJECT.reservation.Reservation;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String title;
    private String description;

    @Column(name = "event_date")
    private LocalDate eventDate;

    private String location;
    private int availableSeats;

    @ManyToOne
    @JoinColumn(name = "organizer_id", nullable = false)
    private AppUser organizer;

}

