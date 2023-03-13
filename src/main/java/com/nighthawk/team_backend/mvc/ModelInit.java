package com.nighthawk.team_backend.mvc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import com.nighthawk.team_backend.mvc.jokes.Jokes;
import com.nighthawk.team_backend.mvc.jokes.JokesJpaRepository;
import com.nighthawk.team_backend.mvc.database.note.Note;
import com.nighthawk.team_backend.mvc.database.note.NoteJpaRepository;
import com.nighthawk.team_backend.mvc.event.Event;
import com.nighthawk.team_backend.mvc.event.EventJpaRepository;
import com.nighthawk.team_backend.mvc.database.club.Club;
import com.nighthawk.team_backend.mvc.database.club.ClubDetailsService;

import java.util.List;

@Component // Scans Application for ModelInit Bean, this detects CommandLineRunner
public class ModelInit {
    @Autowired
    JokesJpaRepository jokesRepo;
    @Autowired
    EventJpaRepository eventRepo;
    @Autowired
    NoteJpaRepository noteRepo;
    @Autowired
    ClubDetailsService clubService;

    @Bean
    CommandLineRunner run() { // The run() method will be executed after the application starts
        return args -> {

            // Joke database is populated with starting jokes
            String[] jokesArray = Jokes.init();
            for (String joke : jokesArray) {
                List<Jokes> jokeFound = jokesRepo.findByJokeIgnoreCase(joke); // JPA lookup
                if (jokeFound.size() == 0)
                    jokesRepo.save(new Jokes(null, joke, 0, 0)); // JPA save
            }

            String[] eventArray = Event.init();
            for (String event : eventArray) {
                List<Event> eventFound = eventRepo.findByEventIgnoreCase(event); // JPA lookup
                if (eventFound.size() == 0)
                eventRepo.save(new Event(null, event, 0, 0)); // JPA save
            }

            // Person database is populated with test data
            Club[] clubArray = Club.init();
            for (Club club : clubArray) {
                // findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase
                List<Club> clubFound = clubService.list(club.getName(), club.getEmail()); // lookup
                if (clubFound.size() == 0) {
                    clubService.save(club); // save

                    // Each "test person" starts with a "test note"
                    // String text = "note 1 for " + club.getName();
                    String text = "note 1 for " + club.getName();
                    Note n = new Note(text, club); // constructor uses new person as Many-to-One association
                    noteRepo.save(n); // JPA Save
                }
            }

        };
    }
}
