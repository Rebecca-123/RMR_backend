package com.nighthawk.team_backend.mvc.database.team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/team")
public class TeamApiController {
    // @Autowired
    // private JwtTokenUtil jwtGen;
    /*
     * #### RESTful API ####
     * Resource: https://spring.io/guides/gs/rest-service/
     */

    // Autowired enables Control to connect POJO Object through JPA
    @Autowired
    private TeamDetailsService repository;
    @Autowired
    private TeamJpaRepository jparepository;
    @Autowired
    private TeamSearchJpaRepository teamSearchJpaRepository;

    /*
     * GET List of Teams
     */
    @GetMapping("/")
    public ResponseEntity<List<Team>> getTeams() {
        return new ResponseEntity<>(repository.listAll(), HttpStatus.OK);
    }

    /*
     * GET individual Team using ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Team> getTeam(@PathVariable long id) {
        Optional<Team> optional = jparepository.findById(id);
        if (optional.isPresent()) { // Good ID
            Team team = optional.get(); // value from findByID
            return new ResponseEntity<>(team, HttpStatus.OK); // OK HTTP response: status code, headers, and body
        }
        // Bad ID
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    // /*
    // * DELETE individual Team using ID
    // */
    // @DeleteMapping("/delete/{id}")
    // public ResponseEntity<Team> deleteTeam(@PathVariable long id) {
    // Optional<Team> optional = jparepository.findById(id);
    // if (optional.isPresent()) { // Good ID
    // Team team = optional.get(); // value from findByID
    // jparepository.deleteById(id); // value from findByID
    // return new ResponseEntity<>(team, HttpStatus.OK); // OK HTTP response: status
    // code, headers, and body
    // }
    // // Bad ID
    // return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    // }

    /*
     * DELETE individual Team using ID, but with POST
     */
    @PostMapping("/delete/{id}")
    public ResponseEntity<Team> deleteTeam(@PathVariable long id) {
        Optional<Team> optional = jparepository.findById(id);
        if (optional.isPresent()) { // Good ID
            Team team = optional.get(); // value from findByID
            jparepository.deleteById(id); // value from findByID
            return new ResponseEntity<>(team, HttpStatus.OK); // OK HTTP response: status code, headers, and body
        }
        // Bad ID
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/post")
    public ResponseEntity<Object> postTeam(@RequestBody Team team) {
        // check for duplicate
        if (jparepository.findByNames(team.getNames()) != null) {
            // Return an error response indicating that the email is already in use
            return new ResponseEntity<>("Email already in use", HttpStatus.CONFLICT);
        }
        repository.save(team);
        return new ResponseEntity<>(team.getNames() + " was created successfully", HttpStatus.CREATED);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Object> updateTeam(@PathVariable long id, @RequestBody Team team) {
        Optional<Team> optional = jparepository.findById(id);
        if (optional.isPresent()) { // Good ID
            Team oldTeam = optional.get(); // value from findByID
            // update attributes of the team
            oldTeam.setPassword(team.getPassword());
            repository.save(oldTeam); // save changes to team
            return new ResponseEntity<>(oldTeam.getNames() + " was updated successfully", HttpStatus.OK); // OK HTTP
            // response: status
            // code, headers,
            // and body
        }
        // Bad ID
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /*
     * The teamSearch API looks across database for partial match to term (k,v)
     * passed by RequestEntity body
     */
    @PostMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> teamSearch(@RequestBody final Map<String, String> map) {
        // extract term from RequestEntity
        String term = (String) map.get("term");
        // these "terms" are same as used in frontend

        // save a search
        TeamSearch searchEntry = new TeamSearch(term);
        teamSearchJpaRepository.saveAndFlush(searchEntry);

        // get how many searches have been done
        long searchCount = teamSearchJpaRepository.count();

        // JPA query to filter on term
        // origionally was jsut list like but mort said by native is better because more
        // precise
        List<Team> list = repository.listLikeNative(term);

        // result should includes teams and how many searches have been done so far
        TeamSearchResult result = new TeamSearchResult(list, searchCount);

        // return resulting list and status, error checking should be added
        // return new ResponseEntity<>(list, HttpStatus.OK);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /*
     * The teamSearch API looks across database for partial match to term (k,v)
     * passed by RequestEntity body
     */
    @PostMapping(value = "/clearHistory", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> clearHistory() {

        // clear history in the database
        teamSearchJpaRepository.deleteAll();
        long searchCount = teamSearchJpaRepository.count();
        ClearHistoryResult result = new ClearHistoryResult(searchCount);

        // return resulting list and status, error checking should be added
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
