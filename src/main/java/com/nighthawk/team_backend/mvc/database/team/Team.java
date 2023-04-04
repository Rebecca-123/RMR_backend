package com.nighthawk.team_backend.mvc.database.team;

import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import static javax.persistence.FetchType.EAGER;

import javax.persistence.*;
import javax.validation.constraints.*;

// import com.nighthawk.team_backend.mvc.database.note.Note;

/*
Team is a POJO, Plain Old Java Object.
First set of annotations add functionality to POJO
--- @Setter @Getter @ToString @NoArgsConstructor @RequiredArgsConstructor
The last annotation connect to database
--- @Entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Team {
    // automatic unique identifier for Team record
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private String bigteam;

    // name, password, roles are key to login and authentication
    @NotEmpty
    // @NonNull: Places this in @RequiredArgsConstructor
    @NonNull
    @Size(min = 1, max = 50)
    @Column(unique = true)
    private String names;

    private int period;

    @NotEmpty
    private String password;

    // To be implemented
    // @ManyToMany(fetch = EAGER)
    // private Collection<ClubRole> roles = new ArrayList<>();

    public Team(String bigteam, String names, String password, int period) {
        this.bigteam = bigteam;
        this.names = names;
        this.password = password;
        this.period = period;
    }

    public static Team[] init() {

        // basics of class construction
        Team nhs = new Team();
        nhs.setBigteam("RMR");
        nhs.setNames("Bob, Bob");
        nhs.setPassword("123");
        nhs.setPeriod(1);

        // Array definition and data initialization
        Team clubs[] = { nhs };
        return (clubs);
    }

    public static String toString(Team club) {
        return "{" + "\"ID\": " + club.id + ", \"Big Team\": " + club.bigteam + ", \"Names\": " + club.names
                + ", \"Period\": " + club.period + ", \"Password\": " + club.password + "}";
    }

    // tester method
    public static void main(String[] args) {
        // obtain from initializer
        Team teams[] = init();

        // iterate using "enhanced for loop"
        for (Team team : teams) {
            System.out.println(toString(team)); // print object
        }
    }

}
