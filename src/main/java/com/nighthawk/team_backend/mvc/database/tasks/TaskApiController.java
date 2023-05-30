package com.nighthawk.team_backend.mvc.database.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController // annotation to simplify the creation of RESTful web services
@RequestMapping("/api/task")  // all requests in file begin with this URI
public class TaskApiController { 

    // Autowired enables Control to connect URI request and POJO Object to easily for Database CRUD operations
    @Autowired
    private TaskJpaRepository repository;

    /* GET List of tasks
     * @GetMapping annotation is used for mapping HTTP GET requests onto specific handler methods.
     */
    @GetMapping("/")
    public ResponseEntity<List<Task>> getTask() {
        // ResponseEntity returns List of tasks provide by JPA findAll()
        return new ResponseEntity<>( repository.findAll(), HttpStatus.OK);
    }

    /* Update Like
     * @PutMapping annotation is used for mapping HTTP PUT requests onto specific handler methods.
     * @PathVariable annotation extracts the templated part {id}, from the URI
     */
    @PutMapping("/finish/{id}")
    public ResponseEntity<Task> setLike(@PathVariable long id) {
        /* 
        * Optional (below) is a container object which helps determine if a result is present. 
        * If a value is present, isPresent() will return true
        * get() will return the value.
        */
        Optional<Task> optional = repository.findById(id);
        if (optional.isPresent()) {  // Good ID
            Task task = optional.get();  // value from findByID
            task.setFinished(task.getFinished()+1); // increment value
            repository.save(task);  // save entity
            return new ResponseEntity<>(task, HttpStatus.OK);  // OK HTTP response: status code, headers, and body
        }
        // Bad ID
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  // Failed HTTP response: status code, headers, and body
    }

    /* Update Jeer     
     */
    @PutMapping("/progress/{id}")
    public ResponseEntity<Task> setJeer(@PathVariable long id) {
        Optional<Task> optional = repository.findById(id);
        if (optional.isPresent()) {  // Good ID
            Task task = optional.get();
            task.setProgress(task.getProgress()+1);
            repository.save(task);
            return new ResponseEntity<>(task, HttpStatus.OK);
        }
        // Bad ID
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
