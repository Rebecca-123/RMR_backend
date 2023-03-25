package com.nighthawk.team_backend.mvc.database.reviews;

import com.nighthawk.team_backend.mvc.database.team.Team;
import com.nighthawk.team_backend.mvc.database.team.TeamDetailsService;
import com.nighthawk.team_backend.mvc.database.team.TeamJpaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.List;
import java.util.Optional;

@Controller
public class ReviewViewController {

    @Autowired
    private TeamJpaRepository teamRepo;

    @Autowired
    private ReviewJpaRepository reviewRepository;

    public static String convertMarkdownToHTML(String markdown) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown);
        HtmlRenderer htmlRenderer = HtmlRenderer.builder().build();
        return htmlRenderer.render(document);
    }

    @GetMapping("/database/reviews/{id}")
    public ResponseEntity<List<Review>> reviews(@PathVariable("id") Long id) {
        Optional<Team> optional = teamRepo.findById(id);
        if (optional.isPresent()) { // Good ID
            Team team = optional.get(); // value from findByID
            List<Review> reviews = reviewRepository.findAllByTeam(team);
            return new ResponseEntity<>(reviews, HttpStatus.OK); // OK HTTP response: status code, headers, and body
        }

        // Bad ID
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/database/addreview/{id}")
    public ResponseEntity<Object> reviewsAdd(@PathVariable("id") Long teamId, @RequestBody String assignment,
            @RequestBody double score, @RequestBody String ticket, @RequestBody String comments) {

        Optional<Team> optional = teamRepo.findById(teamId);
        if (optional.isPresent()) { // Good ID
            Team team = optional.get(); // value from findByID
            Review review = new Review(assignment, team, score, ticket, comments);
            reviewRepository.save(review);
            return new ResponseEntity<>("New review is created successfully for Team:" + teamId, HttpStatus.CREATED);
        }

        // Bad ID
        return new ResponseEntity<>("Team not found in team list - Team:" + teamId, HttpStatus.CREATED);
    }
    // MUST ADD UPDATE AND DELETE FUNCTIONS FOR ALL

    // @PostMapping("/database/addticket/{id}")
    // public ResponseEntity<Object> ticketReview(@PathVariable("id") Long id,
    // @RequestBody String text, @RequestBody String ticket, @RequestBody String
    // comments) {

    // Optional<Review> optional = reviewRepository.findById(id);
    // if (optional.isPresent()) { // Good ID
    // Review review = optional.get(); // value from findByID
    // review.setTicket(review.getTicket() + 1); // increment value
    // reviewRepository.save(review); // save entity
    // return new ResponseEntity<>("Review " + id + " ticket made successfully",
    // HttpStatus.OK); // OK HTTP response: status code, headers, and body
    // }
    // // Bad ID
    // return new ResponseEntity<>("Review not found", HttpStatus.BAD_REQUEST); //
    // Failed HTTP response: status code, headers, and body
    // }

    // @PostMapping("/database/comment/{id}")
    // public ResponseEntity<Object> commentReview(@PathVariable("id") Long id,
    // @RequestBody String text, @RequestBody String ticket, @RequestBody String
    // comments) {

    // Optional<Review> optional = reviewRepository.findById(id);
    // if (optional.isPresent()) { // Good ID
    // Review review = optional.get(); // value from findByID
    // review.setComments(review.getComments() + 1); // increment value
    // reviewRepository.save(review); // save entity
    // return new ResponseEntity<>("Review " + id + " commented successfully",
    // HttpStatus.OK); // OK HTTP response: status code, headers, and body
    // }
    // // Bad ID
    // return new ResponseEntity<>("Review not found", HttpStatus.BAD_REQUEST); //
    // Failed HTTP response: status code, headers, and body

    // }

}