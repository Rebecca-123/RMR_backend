package com.nighthawk.team_backend.mvc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import com.nighthawk.team_backend.mvc.jokes.Jokes;
import com.nighthawk.team_backend.mvc.jokes.JokesJpaRepository;
import com.nighthawk.team_backend.mvc.database.note.Note;
import com.nighthawk.team_backend.mvc.database.note.NoteJpaRepository;
import com.nighthawk.team_backend.mvc.database.team.Team;
import com.nighthawk.team_backend.mvc.database.team.TeamDetailsService;
import com.nighthawk.team_backend.mvc.event.Event;
import com.nighthawk.team_backend.mvc.event.EventJpaRepository;

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
    TeamDetailsService teamService;

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
            Team[] teamArray = Team.init();
            for (Team team : teamArray) {
                // findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase
                List<Team> teamFound = teamService.list(team.getName(), team.getEmail()); // lookup
                if (teamFound.size() == 0) {
                    teamService.save(team); // save

                    // Each "test person" starts with a "test note"
                    // String text = "note 1 for " + team.getName();
                    String text = "note 1 for " + team.getName();
                    Note n = new Note(text, team); // constructor uses new person as Many-to-One association
                    noteRepo.save(n); // JPA Save
                }
            }

        };
    }
}
