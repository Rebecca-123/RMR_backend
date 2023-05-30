package com.nighthawk.team_backend.mvc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import com.nighthawk.team_backend.mvc.jokes.Jokes;
import com.nighthawk.team_backend.mvc.jokes.JokesJpaRepository;
import com.nighthawk.team_backend.mvc.database.reviews.Review;
import com.nighthawk.team_backend.mvc.database.reviews.ReviewJpaRepository;
import com.nighthawk.team_backend.mvc.database.tasks.Task;
import com.nighthawk.team_backend.mvc.database.tasks.TaskJpaRepository;
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
    TaskJpaRepository taskRepo;
    @Autowired
    ReviewJpaRepository reviewRepo;
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

            String[] taskArray = Task.init();
            for (String task : taskArray) {
                List<Task> taskFound = taskRepo.findByTaskIgnoreCase(task); // JPA lookup
                if (taskFound.size() == 0)
                    taskRepo.save(new Task(null, task, 0, 1)); // JPA save
            }

            // Person database is populated with test data
            Team[] teamArray = Team.init();
            for (Team team : teamArray) {
                // findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase
                List<Team> teamFound = teamService.list(team.getBigteam(), team.getEmail()); // lookup
                if (teamFound.size() == 0) {
                    teamService.save(team); // save

                    Review r = new Review("assignment", team, 0.0, "ticket", "comments");
                    reviewRepo.save(r);

                }
            }

        };
    }
}
