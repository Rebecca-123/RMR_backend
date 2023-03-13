package com.nighthawk.team_backend.mvc.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data  // Annotations to simplify writing code (ie constructors, setters)
@NoArgsConstructor
@AllArgsConstructor
@Entity // Annotation to simplify creating an entity, which is a lightweight persistence domain object. Typically, an entity represents a table in a relational database, and each entity instance corresponds to a row in that table.
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique=true)
    private String event;

    private int attend;
    private int skip;

    // starting jokes
    public static String[] init() {
        final String[] EventArray = {
            "Organize a community-wide donation drive",
            "Black tie gala and silent auction",
            "Charity Artic Monkeys concert",
            "Host a heritage celebration",
            "Organize a cultural food festival",
            "Plan Tourist in Your Own Town events"
        };
        return EventArray;
    }
}
