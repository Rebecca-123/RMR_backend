package com.nighthawk.team_backend.mvc.database.team;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRoleJpaRepository extends JpaRepository<TeamRole, Long> {
    TeamRole findByName(String name);
}