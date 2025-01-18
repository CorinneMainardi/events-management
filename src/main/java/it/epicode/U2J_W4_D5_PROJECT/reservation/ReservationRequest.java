package it.epicode.U2J_W4_D5_PROJECT.reservation;

import it.epicode.U2J_W4_D5_PROJECT.auth.AppUser;
import it.epicode.U2J_W4_D5_PROJECT.event.Event;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReservationRequest {
    @NotNull(message = "the field 'user' cannot be null")
    private Long userId;

    @NotNull(message = "the field 'event' cannot be null")
    private Long eventId;
}
