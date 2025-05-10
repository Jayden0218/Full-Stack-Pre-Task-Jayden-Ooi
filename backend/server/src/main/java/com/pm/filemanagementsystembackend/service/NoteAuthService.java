package com.pm.filemanagementsystembackend.service;

import com.pm.filemanagementsystembackend.dto.NoteRequestDTO;
import com.pm.filemanagementsystembackend.dto.NoteResponseDTO;
import com.pm.filemanagementsystembackend.exception.NoteNotFoundException;
import com.pm.filemanagementsystembackend.mapper.NoteMapper;
import com.pm.filemanagementsystembackend.model.Note;
import com.pm.filemanagementsystembackend.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class NoteAuthService {

    private final NoteRepository noteRepository;

    public NoteAuthService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<NoteResponseDTO> getNoteAuth(String email) {
        List<Note> notes = noteRepository.findByIdentificationIsNotNullAndIdentification(email);
        return notes.stream().map(NoteMapper::toDTO).toList();
    }

    public NoteResponseDTO createNoteAuth(NoteRequestDTO noteRequestDTO) {
        Note newNote = noteRepository.save(NoteMapper.toModel(noteRequestDTO));
        return NoteMapper.toDTO(newNote);
    }

    public NoteResponseDTO updateNoteAuth(UUID id, NoteRequestDTO noteRequestDTO) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException("Note not found with ID: " + id));

        note.setTitle(noteRequestDTO.getTitle());
        note.setContent(noteRequestDTO.getContent());
        note.setColor(noteRequestDTO.getColor());

        Note updatedNote = noteRepository.save(note);

        return NoteMapper.toDTO(updatedNote);
    }

    public void deleteNoteAuth(UUID id) {
        noteRepository.deleteById(id);
    }

}
