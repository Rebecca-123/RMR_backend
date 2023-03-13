package com.nighthawk.team_backend.mvc.database.club;

import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import static javax.persistence.FetchType.EAGER;

import javax.persistence.*;
import javax.validation.constraints.*;

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
public class Club {
    // automatic unique identifier for Person record
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // email, password, roles are key to login and authentication
    @NotEmpty
    @Size(min = 5)
    @Column(unique = true)
    @Email
    private String email;

    @NotEmpty
    private String password;

    // @NonNull: Places this in @RequiredArgsConstructor
    @NonNull
    @Size(min = 2, max = 50, message = "Club Name (2 to 50 chars)")
    private String name;

    // To be implemented
    // @ManyToMany(fetch = EAGER)
    // private Collection<ClubRole> roles = new ArrayList<>();

    private String types;

    /*
     * @NonNull
     * private Note note = new Note("test", this);
     * 
     * public String getNote(){
     * return note.getText();
     * }
     * 
     * public void setNote(String note_input){
     * note.setText(note_input);;
     * }
     * 
     */

    // @NonNull: Places this in @RequiredArgsConstructor
    @NonNull
    @Size(min = 2, max = 300, message = "Club Purpose (2 to 300 chars)")
    private String purpose;

    // @NonNull: Places this in @RequiredArgsConstructor
    @NonNull
    @Size(min = 2, max = 50, message = "Club President (2 to 30 chars)")
    private String president;

    // @NonNull: Places this in @RequiredArgsConstructor
    @NonNull
    @Size(min = 2, max = 50, message = "Staff Advisor (2 to 50 chars)")
    private String advisor;

    // @NonNull: Places this in @RequiredArgsConstructor
    @NonNull
    @Size(min = 2, max = 50, message = "Meeting Time and Location (2 to 50 chars)")
    private String meeting;

    @Size(min = 2, max = 150, message = "Additional Info (2 to 150 chars)")
    private String info;

    @Size(min = 1, max = 1, message = "Official Club? (Y or N)")
    private String official;

    public Club(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    // Initializer used when setting database from an API
    public Club(String email, String password, String name, String types, String purpose, String president,
            String advisor,
            String meeting, String info, String official) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.types = types;
        this.purpose = purpose;
        this.president = president;
        this.advisor = advisor;
        this.meeting = meeting;
        this.info = info;
        this.official = official;
    }

    public static Club[] init() {

        // basics of class construction
        Club nhs = new Club();
        nhs.setEmail("dnhshonorsociety@gmail.com");
        nhs.setPassword("nhs");
        nhs.setName("Del Norte National Honor Society");
        nhs.setTypes("Service");
        nhs.setPurpose(
                "A national volunteer organization for high school students who go out into the community with four pillars in mind: scholarship, service, leadership, and character.");
        nhs.setPresident("Dominic De La Torre");
        nhs.setAdvisor("Mr. Swanson");
        nhs.setMeeting("N/A");
        nhs.setInfo("Website: https://dnhshonorsociety.wixsite.com/dnhs");
        nhs.setOfficial("Y");

        Club ncs = new Club();
        ncs.setEmail("tedison@example.com");
        ncs.setPassword("123toby");
        ncs.setName("Nighthawk Coding Society");
        ncs.setTypes("STEM");
        ncs.setPurpose("CODE CODE CODE!");
        ncs.setPresident("N/A");
        ncs.setAdvisor("Mr. M");
        ncs.setInfo("GitHub: https://github.com/nighthawkcoders");
        ncs.setOfficial("N");

        // Array definition and data initialization
        Club clubs[] = { nhs, ncs };
        return (clubs);
    }

    public static String toString(Club club) {
        return "{" + "\"ID\": " + club.id + ", \"Name\": " + club.name + ", \"Email\": " + club.email
                + ", \"Password\": " + club.password + ", \"Types\":" + club.types + ", \"Purpose\": " + club.purpose
                + ", \"President\": " + club.president
                + ", \"Advisor\": " + club.advisor + ", \"Meeting Time and Location\": " + club.meeting
                + ", \"Additional Info\": " + club.info + ", \"Official Club\": " + club.official + "}";
    }

    // tester method
    public static void main(String[] args) {
        // obtain from initializer
        Club clubs[] = init();

        // iterate using "enhanced for loop"
        for (Club club : clubs) {
            System.out.println(toString(club)); // print object
        }
    }

}