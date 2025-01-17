package it.epicode.U2J_W4_D5_PROJECT.auth;

import it.epicode.U2J_W4_D5_PROJECT.event.Event;
import it.epicode.U2J_W4_D5_PROJECT.reservation.Reservation;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @OneToMany(mappedBy = "organizer")
    private Set<Event> events;

    @OneToMany(mappedBy = "user")
    private Set<Reservation> reservations;

}
