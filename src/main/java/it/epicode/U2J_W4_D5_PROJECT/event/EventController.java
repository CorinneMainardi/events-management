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
    public ResponseEntity<List<Event>>getAllEvents(){
        return ResponseEntity.ok(eventSvc.getAllEvents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event>findEventById(@PathVariable Long id){
        return ResponseEntity.ok(eventSvc.getEventById(id));
    }
    @PostMapping
    public ResponseEntity<Event>createEvent(@RequestBody EventRequest eventRequest, @RequestHeader("Authorization") String token){
        String username = jwtTokenUtil.getUsernameFromToken(token);

        AppUser appUser = appUserService.loadUserByUsername(username);


        if (!appUser.getRoles().contains(Role.ROLE_ORGANISER)) {
            throw new RuntimeException("Only event organizers can create events");
        }
        if (!appUser.getRoles().contains(Role.ROLE_ORGANISER)) {
            throw new RuntimeException("Only event organizers can create events");
        }


        return ResponseEntity.status(HttpStatus.CREATED).body(eventSvc.createEvent(eventRequest, appUser));
    }



    @PutMapping("/{id}")
    public ResponseEntity<Event>updateEvent(@RequestBody EventRequest eventRequest, @PathVariable Long id ){
        return ResponseEntity.ok(eventSvc.updateEvent(id, eventRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Event> deleteEmployee(@PathVariable Long id){
        return ResponseEntity.ok(eventSvc.deleteEvent(id));
    }
}
