package com.nighthawk.team_backend.mvc.database.team;

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
public class TeamSearch {
    // automatic unique identifier for Person record
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String term;

    public TeamSearch(String term) {
        this.term = term;
    }

    // Initializer used when setting database from an API

    public static TeamSearch[] init() {

        // basics of class construction
        TeamSearch search1 = new TeamSearch();
        search1.setTerm("Society");

        // Array definition and data initialization
        TeamSearch searches[] = { search1 };
        return (searches);
    }

    public static String toString(TeamSearch search) {
        return "{" + "\"ID\": " + search.id + ", \"Term\": " + search.term + "}";
    }

    // tester method
    public static void main(String[] args) {
        // obtain from initializer
        TeamSearch searches[] = init();

        for (TeamSearch search : searches) {
            System.out.println(toString(search)); // print object
        }
    }
}