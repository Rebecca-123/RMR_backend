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
    private String text;

    @Column(columnDefinition="TEXT")
    private String ticket;

    @Column(columnDefinition="TEXT")
    private String comments;

    public Review(String text, Club c, String ticket, String comments) {
        this.text = text;
        this.club = c;
        this.ticket = ticket;
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Review [club=" + club.getName() + ", text=" + text + ", ticket=" + ticket + ", comments=" + comments + "]";
    }

    public static void main(String[] args) {
        Club c = new Club ("hi@gmail.com", "abc123", "Test Club");
        Review review1 = new Review("First Review for Club", c, "githuburl", "something");
        
        System.out.println("Review 1: " + review1.toString());

    }
}

