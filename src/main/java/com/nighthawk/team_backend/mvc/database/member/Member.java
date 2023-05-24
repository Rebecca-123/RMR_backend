package com.nighthawk.team_backend.mvc.database.member;

public class Member {
    // Key instance variables
    private String name;
    private String githubId;
    private String blog;

    public Member(String name, String githubId, String blog) {
        this.name = name;
        this.githubId = githubId;
        this.blog = blog;
    }

    public String getName() {
        return this.name;
    }

    public String getGithubId() {
        return this.githubId;
    }

    public String getBlog() {
        return this.blog;
    }
}
