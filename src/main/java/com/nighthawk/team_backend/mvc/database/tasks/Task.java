package com.nighthawk.team_backend.mvc.database.tasks;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data  // Annotations to simplify writing code (ie constructors, setters)
@NoArgsConstructor
@AllArgsConstructor
@Entity // Annotation to simplify creating an entity, which is a lightweight persistence domain object. Typically, an entity represents a table in a relational database, and each entity instance corresponds to a row in that table.
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique=true)
    private String task;

    private int finished;
    private int progress;

    // starting jokes
    public static String[] init() {
        final String[] TaskArray = {
            "Set Up RDS",
            "Create Wireframe",
            "Set up Frontend",
            "Import HTML template",
            "Create a POJO",
            "AWS Deploy" 
        };
        return TaskArray;
    }
}
