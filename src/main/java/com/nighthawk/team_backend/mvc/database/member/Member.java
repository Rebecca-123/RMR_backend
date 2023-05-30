package com.nighthawk.team_backend.mvc.database.member;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data // Annotations to simplify writing code (ie constructors, setters)
public class Member implements Serializable {
    String name;

    String githubId;

    String blog;
    
}