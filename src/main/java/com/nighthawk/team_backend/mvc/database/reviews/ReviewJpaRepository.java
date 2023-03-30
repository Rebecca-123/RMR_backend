package com.nighthawk.team_backend.mvc.database.reviews;

import com.nighthawk.team_backend.mvc.database.team.Team;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import javax.transaction.Transactional;


import java.util.List;

public interface ReviewJpaRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByTeam(Team team);

    @Transactional
    void deleteByTeamId(long id);

    List<Review> findAllReviewsById(Long id);

    List<Review> findAllByOrderByTeam();

    @Query(
            value = "SELECT * FROM Review p WHERE p.assignment LIKE ?1 or p.team LIKE ?1",
            nativeQuery = true)
    List<Review> findByLikeTermNative(String term);
}
