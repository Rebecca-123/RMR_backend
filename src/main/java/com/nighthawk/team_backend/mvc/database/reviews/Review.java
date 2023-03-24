package com.nighthawk.team_backend.mvc.database.reviews;
import com.nighthawk.team_backend.mvc.database.club.Club;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name="club_id")
    private Club club;

    @NotNull
    @Column(columnDefinition="TEXT")
    private String assignment;

    @NotNull
    private double score;

    @Column(columnDefinition="TEXT")
    private String ticket;

    @Column(columnDefinition="TEXT")
    private String comments;

    public Review(String assignment, Club c, double score, String ticket, String comments) {
        this.assignment = assignment;
        this.club = c;
        this.score = score;
        this.ticket = ticket;
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Review [club=" + club.getName() + ", assignment=" + assignment + ", score=" + score + ", ticket=" + ticket + ", comments=" + comments + "]";
    }

    public static Review[] init() {

        // basics of class construction
        Review rmr = new Review();
        rmr.setAssignment("assignment 1");
        rmr.setScore(2.7);
        rmr.setTicket("some github url");
        rmr.setComments("awesome");

        Review cancode = new Review();
        cancode.setAssignment("assignment 1");
        cancode.setScore(2.6);
        cancode.setTicket("github url");
        cancode.setComments("mid");

        // Array definition and data initialization
        Review reviews[] = { rmr, cancode };
        return (reviews);
    }

    public static String toString(Review review) {
        return "{" + "\"ID\": " + review.id + ", \"Assignment\": " + review.assignment + ", \"Score\": " + review.score
                + ", \"Ticket\": " + review.ticket + ", \"Comments\":" + review.comments + "}";
    }

    public static void main(String[] args) {
        Club c = new Club ("hi@gmail.com", "abc123", "Test Club");
        Review review1 = new Review("Assignment 1", c, 2.7, "review ticket", "something");
        System.out.println("Review 1: " + review1.toString());
        Review reviews[] = init();

        // iterate using "enhanced for loop"
        for (Review review : reviews) {
            System.out.println(toString(review)); // print object
        }

    }
}

