package com.nighthawk.team_backend.mvc.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController // annotation to simplify the creation of RESTful web services
@RequestMapping("/api/event")  // all requests in file begin with this URI
public class EventApiController {

    // Autowired enables Control to connect URI request and POJO Object to easily for Database CRUD operations
    @Autowired
    private EventJpaRepository repository;

    /* GET List of events
     * @GetMapping annotation is used for mapping HTTP GET requests onto specific handler methods.
     */
    @GetMapping("/")
    public ResponseEntity<List<Event>> getEvent() {
        // ResponseEntity returns List of events provide by JPA findAll()
        return new ResponseEntity<>( repository.findAll(), HttpStatus.OK);
    }

    /* Update Like
     * @PutMapping annotation is used for mapping HTTP PUT requests onto specific handler methods.
     * @PathVariable annotation extracts the templated part {id}, from the URI
     */
    @PutMapping("/attend/{id}")
    public ResponseEntity<Event> setLike(@PathVariable long id) {
        /* 
        * Optional (below) is a container object which helps determine if a result is present. 
        * If a value is present, isPresent() will return true
        * get() will return the value.
        */
        Optional<Event> optional = repository.findById(id);
        if (optional.isPresent()) {  // Good ID
            Event event = optional.get();  // value from findByID
            event.setAttend(event.getAttend()+1); // increment value
            repository.save(event);  // save entity
            return new ResponseEntity<>(event, HttpStatus.OK);  // OK HTTP response: status code, headers, and body
        }
        // Bad ID
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  // Failed HTTP response: status code, headers, and body
    }

    /* Update Jeer
     */
    @PutMapping("/skip/{id}")
    public ResponseEntity<Event> setJeer(@PathVariable long id) {
        Optional<Event> optional = repository.findById(id);
        if (optional.isPresent()) {  // Good ID
            Event event = optional.get();
            event.setSkip(event.getSkip()+1);
            repository.save(event);
            return new ResponseEntity<>(event, HttpStatus.OK);
        }
        // Bad ID
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
