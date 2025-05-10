package com.pm.filemanagementsystembackend.repository;

import com.pm.filemanagementsystembackend.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NoteRepository extends JpaRepository<Note, UUID> {
    boolean existsByIdNot(UUID id);
    List<Note> findByIdentificationIsNotNullAndIdentification(String identification);
}
