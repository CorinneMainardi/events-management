package it.epicode.U2J_W4_D5_PROJECT.reservation;

import it.epicode.U2J_W4_D5_PROJECT.auth.AppUser;
import it.epicode.U2J_W4_D5_PROJECT.event.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    @Autowired
    private ReservationSvc reservationSvc;

    @GetMapping
    public ResponseEntity<List<Reservation>> finAllReservation(){
        return ResponseEntity.ok(reservationSvc.findAllReservation());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Reservation>findReservationById(@PathVariable Long id){
        return ResponseEntity.ok(reservationSvc.findReservationById(id));
    }

    @PostMapping
    @PreAuthorize(" hasRole('ROLE_USER')")
    public ResponseEntity<Reservation>reserveSeat(@RequestBody ReservationRequest reservationRequest, @AuthenticationPrincipal AppUser appUser){
        Reservation reservation = reservationSvc.reserveSeat(reservationRequest, appUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(reservation);
    }
    @PutMapping("/{id}")
    @PreAuthorize(" hasRole('ROLE_USER')")
    public ResponseEntity<Reservation>updateReservation(@RequestBody ReservationRequest reservationRequest, @PathVariable Long id,  @AuthenticationPrincipal AppUser appUser ){
        return ResponseEntity.ok(reservationSvc.updateReservation(id, reservationRequest));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Reservation> deleteReservation(@PathVariable Long userId, Long eventId, @AuthenticationPrincipal AppUser appUser){
        reservationSvc.deleteReservation(userId, eventId);
        return ResponseEntity.noContent().build();
    }
}

