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

    @Column(columnDefinition="NUMBER")
    private int likes;

    @Column(columnDefinition="NUMBER")
    private int dislikes;

    public Review(String text, Club c) {
        this.text = text;
        this.club = c;
        this.likes = 0;
        this.dislikes = 0;
    }

    @Override
    public String toString() {
        return "Review [club=" + club.getName() + ", text=" + text + ", likes=" + likes + ", dislikes=" + dislikes + "]";
    }

    public static void main(String[] args) {
        Club c = new Club ("hi@gmail.com", "abc123", "Test Club");
        Review review1 = new Review("First Review for Club", c);
        
        System.out.println("Review 1: " + review1.toString());

    }
}

