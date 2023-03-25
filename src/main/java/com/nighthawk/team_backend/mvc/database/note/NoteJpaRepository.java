package com.nighthawk.team_backend.mvc.database.note;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteJpaRepository extends JpaRepository<Note, Long> {
    List<Note> findByTeamId(Long id);

    @Transactional
    void deleteByTeamId(long id);

    List<Note> findAllNotesById(Long id);

}
