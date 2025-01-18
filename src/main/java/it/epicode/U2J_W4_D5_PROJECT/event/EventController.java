package it.epicode.U2J_W4_D5_PROJECT.event;

import it.epicode.U2J_W4_D5_PROJECT.auth.AppUser;
import it.epicode.U2J_W4_D5_PROJECT.auth.AppUserService;
import it.epicode.U2J_W4_D5_PROJECT.auth.JwtTokenUtil;
import it.epicode.U2J_W4_D5_PROJECT.auth.Role;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {
    @Autowired
    private EventSvc eventSvc;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private AppUserService appUserService;


    @GetMapping
    public ResponseEntity<List<Event>>finAllEvents(){
        return ResponseEntity.ok(eventSvc.getAllEvents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event>findEventById(@PathVariable Long id){
        return ResponseEntity.ok(eventSvc.getEventById(id));
    }

    @PostMapping
    @PreAuthorize(" hasRole('ROLE_ORGANISER')")
    public ResponseEntity<Event> createEvent(@RequestBody EventRequest eventRequest, @AuthenticationPrincipal AppUser appUser) {
        if (appUser == null) {
            throw new AuthenticationCredentialsNotFoundException("User is not authenticated");
        }

        if (!appUser.getRoles().contains(Role.ROLE_ORGANISER)) {
            throw new AccessDeniedException("You do not have permission to create an event.");
        }


        Event event = eventSvc.createEvent(eventRequest, appUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(event);
    }



    @PutMapping("/{id}")
    @PreAuthorize(" hasRole('ROLE_ORGANISER')")
    public ResponseEntity<Event>updateEvent(@RequestBody EventRequest eventRequest, @PathVariable Long id, @AuthenticationPrincipal AppUser appUser){
        return ResponseEntity.ok(eventSvc.updateEvent(id, eventRequest));
    }
    @PreAuthorize(" hasRole('ROLE_ORGANISER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Event> deleteEmployee(@PathVariable Long id, @AuthenticationPrincipal AppUser appUser){
        return ResponseEntity.ok(eventSvc.deleteEvent(id));
    }
}
