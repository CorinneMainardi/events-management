package it.epicode.U2J_W4_D5_PROJECT.event;


import it.epicode.U2J_W4_D5_PROJECT.auth.AppUser;
import it.epicode.U2J_W4_D5_PROJECT.exceptions.UploadException;
import it.epicode.U2J_W4_D5_PROJECT.reservation.ReservationRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated

public class EventSvc {
    @Autowired
    EventRepository eventRepository;
    @Autowired
    ReservationRepository reservationRepository;

    public List<Event> getAllEvents(){
        return eventRepository.findAll();
    }

    public Event getEventById(Long id){
        if(!eventRepository.existsById(id)){
            throw new EntityNotFoundException("Event not found");
        }
        return eventRepository.findById(id).get();
    }
    public Event createEvent(@Valid EventRequest eventRequest,  AppUser appUser ) {
        try {

            Event e = new Event();
            if (eventRequest.getAvailableSeats() <= 0) {
                throw new RuntimeException("Available seats must be greater than zero");
            }
            BeanUtils.copyProperties(eventRequest, e);
            e.setOrganizer(appUser);
            return eventRepository.save(e);

        } catch (UploadException ex) {

            throw new RuntimeException("Failed to create event due to upload issue: " + ex.getMessage(), ex);

        } catch (Exception ex) {

            throw new RuntimeException("An unexpected error occurred while creating the event: " + ex.getMessage(), ex);
        }
    }


    public Event updateEvent( Long id,@Valid EventRequest eventRequest) {
        try {
            Event e = getEventById(id);
            if (eventRepository.findById(id).isPresent()) {

                BeanUtils.copyProperties(eventRequest,e);


            } else {
                throw new RuntimeException("Event with id number"+ id + "not found");
            }


            return eventRepository.save(e);

        } catch (UploadException ex) {
            throw new RuntimeException("Failed to update event due to upload issue: " + ex.getMessage(), ex);

        } catch (Exception ex) {
            throw new RuntimeException("An unexpected error occurred while updating the event: " + ex.getMessage(), ex);
        }
    }

    @Transactional
    public Event deleteEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        reservationRepository.deleteByEvent(event);

        eventRepository.delete(event);
        return event;
    }






}
