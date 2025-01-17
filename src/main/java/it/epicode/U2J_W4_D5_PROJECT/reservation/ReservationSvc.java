package it.epicode.U2J_W4_D5_PROJECT.reservation;

import it.epicode.U2J_W4_D5_PROJECT.auth.AppUser;
import it.epicode.U2J_W4_D5_PROJECT.auth.AppUserRepository;
import it.epicode.U2J_W4_D5_PROJECT.event.Event;
import it.epicode.U2J_W4_D5_PROJECT.event.EventRepository;
import it.epicode.U2J_W4_D5_PROJECT.exceptions.ReservationException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReservationSvc {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private AppUserRepository userRepository;

    public List<Reservation> getAllReservation(){
        return reservationRepository.findAll();
    }


    public Reservation getReservationById(Long id){
        if(!reservationRepository.existsById(id)){
            throw new EntityNotFoundException("Reservation not found");
        }
        return reservationRepository.findById(id).get();
    }

    @Transactional
    public Reservation reserveSeat(Long userId, Long eventId) {
        try {

            AppUser user = userRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("User with ID " + userId + " not found"));

            Event event = eventRepository.findById(eventId)
                    .orElseThrow(() -> new EntityNotFoundException("Event with ID " + eventId + " not found"));

            if (event.getAvailableSeats() <= 0) {
                throw new ReservationException("No available seats for event ID " + eventId);
            }

            Reservation reservation = new Reservation();
            reservation.setUser(user);
            reservation.setEvent(event);
            event.setAvailableSeats(event.getAvailableSeats() - 1);

            eventRepository.save(event);
            return reservationRepository.save(reservation);
        } catch (Exception ex) {
            throw new RuntimeException("An unexpected error occurred while creating the reservation: " + ex.getMessage(), ex);
        }
    }

    public List<Reservation> getUserReservations(Long userId) {
        AppUser user = userRepository.findById(userId).orElseThrow(() -> new ReservationException("User not found"));
        return reservationRepository.findByUser(user);
    }
@Transactional
    public void deleteReservation(Long userId, Long eventId) {
        try {

            AppUser user = userRepository.findById(userId).orElseThrow(() -> new ReservationException("User not found"));

            Event event = eventRepository.findById(eventId).orElseThrow(() -> new ReservationException("Event not found"));

            Reservation reservation = reservationRepository.findByUserAndEvent(user, event)
                    .orElseThrow(() -> new ReservationException("Reservation not found for this user and event"));

            reservationRepository.delete(reservation);

            event.setAvailableSeats(event.getAvailableSeats() + 1);

            eventRepository.save(event);

        } catch (Exception ex) {

            throw new RuntimeException("An unexpected error occurred while deleting the reservation: " + ex.getMessage(), ex);
        }
    }

}
