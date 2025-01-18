package it.epicode.U2J_W4_D5_PROJECT.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import it.epicode.U2J_W4_D5_PROJECT.auth.AppUser;
import it.epicode.U2J_W4_D5_PROJECT.auth.Role;
import it.epicode.U2J_W4_D5_PROJECT.reservation.Reservation;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class EventRequest {
    @FutureOrPresent
    @NotNull(message = "the field 'date' cannot be null")
    @JsonFormat(pattern = "yyyy-MM-dd")

    private LocalDate eventDate;
    @NotBlank (message = "the field 'location' cannot be blank")
    private String location;
    @NotBlank (message = "the field 'eventname' cannot be blank")
    private String title;
    @NotBlank (message = "the field 'description' cannot be blank")
    private String description;
    @NotNull (message = "the field 'availableSeats' cannot be null")
    private int availableSeats;

}
