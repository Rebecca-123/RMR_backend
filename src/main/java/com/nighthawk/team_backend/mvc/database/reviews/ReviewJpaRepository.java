package com.nighthawk.team_backend.mvc.database.reviews;

import com.nighthawk.team_backend.mvc.database.team.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewJpaRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByTeam(Team team);
}
