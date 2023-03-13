package com.nighthawk.team_backend.mvc.database.club;

import lombok.*;
import javax.persistence.*;

// import com.nighthawk.team_backend.mvc.database.note.Note;

/*
Person is a POJO, Plain Old Java Object.
First set of annotations add functionality to POJO
--- @Setter @Getter @ToString @NoArgsConstructor @RequiredArgsConstructor
The last annotation connect to database
--- @Entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ClubSearch {
    // automatic unique identifier for Person record
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String term;

    public ClubSearch(String term) {
        this.term = term;
    }

    // Initializer used when setting database from an API

    public static ClubSearch[] init() {

        // basics of class construction
        ClubSearch search1 = new ClubSearch();
        search1.setTerm("Society");
        
        // Array definition and data initialization
        ClubSearch searches[] = { search1 };
        return (searches);
    }

    public static String toString(ClubSearch search) {
        return "{" + "\"ID\": " + search.id + ", \"Term\": " + search.term + "}";
    }

    // tester method
    public static void main(String[] args) {
        // obtain from initializer
        ClubSearch searches[] = init();

        for (ClubSearch search : searches) {
            System.out.println(toString(search)); // print object
        }
    }
}