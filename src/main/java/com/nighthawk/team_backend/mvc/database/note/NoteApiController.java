package com.nighthawk.team_backend.mvc.database.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.nighthawk.team_backend.mvc.database.team.Team;
import com.nighthawk.team_backend.mvc.database.team.TeamJpaRepository;

import java.util.*;

@RestController
@RequestMapping("/api/note")
public class NoteApiController {
    // Autowired enables Control to connect POJO Object through JPA
    @Autowired
    private NoteJpaRepository notejparepository;

    @Autowired
    private TeamJpaRepository jparepository;

    /*
     * GET List of People
     */
    /*
     * GET individual Team using ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<List<Note>> getNote(@PathVariable Long id)
            throws JsonMappingException, JsonProcessingException {
        List<Note> note = notejparepository.findAllNotesById(id);
        return new ResponseEntity<>(note, HttpStatus.OK);
    }

    /*
     * POST Aa record by Requesting Parameters from URI
     */

    @PostMapping("/post/{id}")
    public ResponseEntity<Object> note_result(@PathVariable Long id, @RequestBody final Map<String, String> map) {
        Optional<Team> optional = jparepository.findById(id);
        Team club = optional.get(); // value from findByID
        String text = (String) map.get("text");

        Note note = new Note(text, club);

        notejparepository.save(note);
        return new ResponseEntity<>("Note for club: " + club.getName() + " was created successfully",
                HttpStatus.CREATED);
    }
}