package it.epicode.U2J_W4_D5_PROJECT.reservation;

import it.epicode.U2J_W4_D5_PROJECT.auth.AppUser;
import it.epicode.U2J_W4_D5_PROJECT.event.Event;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

public class ReservationRequest {
    @NotNull
    private AppUser user;

    @NotNull
    private Event event;
}
