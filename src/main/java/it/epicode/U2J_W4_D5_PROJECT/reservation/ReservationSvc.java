package it.epicode.U2J_W4_D5_PROJECT.reservation;

import it.epicode.U2J_W4_D5_PROJECT.auth.AppUser;
import it.epicode.U2J_W4_D5_PROJECT.auth.AppUserRepository;
import it.epicode.U2J_W4_D5_PROJECT.auth.Role;
import it.epicode.U2J_W4_D5_PROJECT.event.Event;
import it.epicode.U2J_W4_D5_PROJECT.event.EventRepository;
import it.epicode.U2J_W4_D5_PROJECT.exceptions.AlreadyExistsException;
import it.epicode.U2J_W4_D5_PROJECT.exceptions.InternalServerErrorException;
import it.epicode.U2J_W4_D5_PROJECT.exceptions.ReservationException;
import it.epicode.U2J_W4_D5_PROJECT.exceptions.UploadException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class ReservationSvc {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private AppUserRepository userRepository;

    public List<Reservation> findAllReservation(){
        return reservationRepository.findAll();
    }


    public Reservation findReservationById(Long id){
        if(!reservationRepository.existsById(id)){
            throw new EntityNotFoundException("Reservation not found");
        }
        return reservationRepository.findById(id).get();
    }
    public List<Reservation> getUserReservations(Long userId) {
        AppUser user = userRepository.findById(userId).orElseThrow(() -> new ReservationException("User not found"));
        return reservationRepository.findByUser(user);
    }
    @Transactional
    public Reservation reserveSeat(@Valid ReservationRequest reservationRequest, @AuthenticationPrincipal AppUser appUser) {
        try {

            if (!appUser.getRoles().contains(Role.ROLE_USER)) {
                throw new AccessDeniedException("You do not have permission to reserve a seat.");
            }

            AppUser user = userRepository.findById(reservationRequest.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with ID " + reservationRequest.getUserId()));
            Event event = eventRepository.findById(reservationRequest.getEventId())
                    .orElseThrow(() -> new EntityNotFoundException("Event not found with ID " + reservationRequest.getEventId()));


            boolean reservationExists = reservationRepository.existsByUserAndEvent(user, event);
            if (reservationExists) {
                throw new AlreadyExistsException("User already has a reservation for this event");
            }


            if (event.getAvailableSeats() <= 0) {
                throw new ReservationException("No available seats for event ID " + event.getId());
            }


            Reservation reservation = new Reservation();
            reservation.setUser(user);
            reservation.setEvent(event);

            event.setAvailableSeats(event.getAvailableSeats() - 1);
            eventRepository.save(event);

            return reservationRepository.save(reservation);


        } catch (Exception ex) {
            ex.printStackTrace();
            throw new InternalServerErrorException("An error occurred while creating the reservation: " + ex.getMessage());
        }
    }



@Transactional
    public Reservation deleteReservation(Long userId, Long eventId) {
        try {

            AppUser user = userRepository.findById(userId).orElseThrow(() -> new ReservationException("User not found"));

            Event event = eventRepository.findById(eventId).orElseThrow(() -> new ReservationException("Event not found"));

            Reservation reservation = reservationRepository.findByUserAndEvent(user, event)
                    .orElseThrow(() -> new ReservationException("Reservation not found for this user and event"));

            reservationRepository.delete(reservation);

            event.setAvailableSeats(event.getAvailableSeats() + 1);

            eventRepository.save(event);
            return reservation;
        } catch (Exception ex) {

            throw new InternalServerErrorException("An unexpected error occurred while deleting the reservation: " + ex.getMessage());
        }
    }

}
