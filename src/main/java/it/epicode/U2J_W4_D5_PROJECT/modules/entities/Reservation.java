package it.epicode.U2J_W4_D5_PROJECT.modules.entities;

import it.epicode.U2J_W4_D5_PROJECT.auth.entities.AppUser;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;


}
