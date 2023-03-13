package com.nighthawk.team_backend.mvc.database.club;

import java.util.*;

public class ClubSearchResult {
    /**
     *
     */
    public List<Club> clubs = new ArrayList<Club>();
    public long searchCount = 0;

    public ClubSearchResult(List<Club> clubs, long searchCount) {
        this.clubs = clubs;
        this.searchCount = searchCount;
    }
}
