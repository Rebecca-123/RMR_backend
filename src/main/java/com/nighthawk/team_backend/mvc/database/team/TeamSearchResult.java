package com.nighthawk.team_backend.mvc.database.team;

import java.util.*;

public class TeamSearchResult {
    /**
     *
     */
    public List<Team> clubs = new ArrayList<Team>();
    public long searchCount = 0;

    public TeamSearchResult(List<Team> clubs, long searchCount) {
        this.clubs = clubs;
        this.searchCount = searchCount;
    }
}
