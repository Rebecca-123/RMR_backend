package com.nighthawk.team_backend.mvc.database.club;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/club")
public class ClubApiController {
    // @Autowired
    // private JwtTokenUtil jwtGen;
    /*
     * #### RESTful API ####
     * Resource: https://spring.io/guides/gs/rest-service/
     */

    // Autowired enables Control to connect POJO Object through JPA
    @Autowired
    private ClubDetailsService repository;
    @Autowired
    private ClubJpaRepository jparepository;
    @Autowired
    private ClubSearchJpaRepository clubSearchJpaRepository;    

    /*
     * GET List of Clubs
     */
    @GetMapping("/")
    public ResponseEntity<List<Club>> getClubs() {
        return new ResponseEntity<>(repository.listAll(), HttpStatus.OK);
    }

    /*
     * GET individual Club using ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Club> getClub(@PathVariable long id) {
        Optional<Club> optional = jparepository.findById(id);
        if (optional.isPresent()) { // Good ID
            Club club = optional.get(); // value from findByID
            return new ResponseEntity<>(club, HttpStatus.OK); // OK HTTP response: status code, headers, and body
        }
        // Bad ID
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    // /*
    // * DELETE individual Club using ID
    // */
    // @DeleteMapping("/delete/{id}")
    // public ResponseEntity<Club> deleteClub(@PathVariable long id) {
    // Optional<Club> optional = jparepository.findById(id);
    // if (optional.isPresent()) { // Good ID
    // Club club = optional.get(); // value from findByID
    // jparepository.deleteById(id); // value from findByID
    // return new ResponseEntity<>(club, HttpStatus.OK); // OK HTTP response: status
    // code, headers, and body
    // }
    // // Bad ID
    // return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    // }

    /*
     * DELETE individual Club using ID, but with POST
     */
    @PostMapping("/delete/{id}")
    public ResponseEntity<Club> deleteClub(@PathVariable long id) {
        Optional<Club> optional = jparepository.findById(id);
        if (optional.isPresent()) { // Good ID
            Club club = optional.get(); // value from findByID
            jparepository.deleteById(id); // value from findByID
            return new ResponseEntity<>(club, HttpStatus.OK); // OK HTTP response: status code, headers, and body
        }
        // Bad ID
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/post")
    public ResponseEntity<Object> postClub(@RequestBody Club club) {
        // check for duplicate
        if (jparepository.findByEmail(club.getEmail()) != null) {
            // Return an error response indicating that the email is already in use
            return new ResponseEntity<>("Email already in use", HttpStatus.CONFLICT);
        }
        repository.save(club);
        return new ResponseEntity<>(club.getEmail() + " was created successfully", HttpStatus.CREATED);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Object> updateClub(@PathVariable long id, @RequestBody Club club) {
        Optional<Club> optional = jparepository.findById(id);
        if (optional.isPresent()) { // Good ID
            Club oldClub = optional.get(); // value from findByID
            // update attributes of the club
            oldClub.setEmail(club.getEmail());
            oldClub.setPassword(club.getPassword());
            oldClub.setName(club.getName());
            oldClub.setTypes(club.getTypes());
            oldClub.setPurpose(club.getPurpose());
            oldClub.setPresident(club.getPresident());
            oldClub.setAdvisor(club.getAdvisor());
            oldClub.setMeeting(club.getMeeting());
            oldClub.setInfo(club.getInfo());
            oldClub.setOfficial(club.getOfficial());
            repository.save(oldClub); // save changes to club
            return new ResponseEntity<>(oldClub.getName() + " was updated successfully", HttpStatus.OK); // OK HTTP
            // response: status
            // code, headers,
            // and body
        }
        // Bad ID
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /*
     * The clubSearch API looks across database for partial match to term (k,v)
     * passed by RequestEntity body
     */
    @PostMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> clubSearch(@RequestBody final Map<String, String> map) {
        // extract term from RequestEntity
        String term = (String) map.get("term");
        //these "terms" are same as used in frontend 

        // save a search
        ClubSearch searchEntry = new ClubSearch(term);
        clubSearchJpaRepository.saveAndFlush(searchEntry);

        // get how many searches have been done
        long searchCount = clubSearchJpaRepository.count();

        // JPA query to filter on term
        //origionally was jsut list like but mort said by native is better because more precise 
        List<Club> list = repository.listLikeNative(term);

        // result should includes clubs and how many searches have been done so far
        ClubSearchResult result = new ClubSearchResult(list, searchCount);

        // return resulting list and status, error checking should be added
        // return new ResponseEntity<>(list, HttpStatus.OK);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    /*
     * The clubSearch API looks across database for partial match to term (k,v)
     * passed by RequestEntity body
     */
    @PostMapping(value = "/clearHistory", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> clearHistory() {

        // clear history in the database
        clubSearchJpaRepository.deleteAll();
        long searchCount = clubSearchJpaRepository.count();
        ClearHistoryResult result = new ClearHistoryResult(searchCount);
        
        // return resulting list and status, error checking should be added
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}    
