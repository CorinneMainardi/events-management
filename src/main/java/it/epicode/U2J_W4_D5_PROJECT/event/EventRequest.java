package it.epicode.U2J_W4_D5_PROJECT.event;

import it.epicode.U2J_W4_D5_PROJECT.auth.AppUser;
import it.epicode.U2J_W4_D5_PROJECT.reservation.Reservation;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class EventRequest {
    @FutureOrPresent
    @NotNull
    private LocalDate date;
    @NotBlank
    private String location;
    @NotBlank
    private String eventName;
    @NotBlank
    private String description;
    @NotNull
    private int availableSeats;
    @NotBlank
    private AppUser organizer;
    @Min(1)
    private Set<Reservation> reservations;
}
