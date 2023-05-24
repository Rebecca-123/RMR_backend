package com.nighthawk.team_backend.mvc.database.team;

import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.FetchType.EAGER;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.nighthawk.team_backend.mvc.database.member.Member;

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

    // email, password, roles are key to login and authentication
    @NotEmpty
    @NonNull
    @Size(min = 5)
    @Column(unique = true)
    @Email
    private String email;

    // @NotEmpty
    // @NonNull
    // @NonNull: Places this in @RequiredArgsConstructor
    private ArrayList<Member> names;
    // creating a member arraylist 

    private int period;

    @NotEmpty
    private String password;

    // To be implemented
    // @ManyToMany(fetch = EAGER)
    // private Collection<ClubRole> roles = new ArrayList<>();

    // constructor
    public Team(String bigteam, String email, String password, int period) {
        this.bigteam = bigteam;
        this.email = email;
        this.password = password;
        this.period = period;
    }

    public static Team[] init() {

        // basics of class construction
        Team nhs = new Team();
        nhs.setBigteam("RMR");
        nhs.setEmail("abc@gmail.com");
        ArrayList<Member> mySet = new ArrayList<Member>();
        Member joe = new Member("test", "test", "test");
        mySet.add(joe);
        nhs.setNames(mySet);
        nhs.setPassword("123");
        nhs.setPeriod(1);

        // Array definition and data initialization
        Team clubs[] = { nhs };
        return (clubs);
    }

    public static String toString(Team club) {
        return "{" + "\"ID\": " + club.id + ", \"Big Team\": " + club.bigteam + ", \"Email\": " + club.email
                + ", \"Names\": " + club.names
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
