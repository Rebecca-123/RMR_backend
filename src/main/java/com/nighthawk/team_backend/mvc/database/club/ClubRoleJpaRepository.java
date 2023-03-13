package com.nighthawk.team_backend.mvc.database.club;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRoleJpaRepository extends JpaRepository<ClubRole, Long> {
    ClubRole findByName(String name);
}