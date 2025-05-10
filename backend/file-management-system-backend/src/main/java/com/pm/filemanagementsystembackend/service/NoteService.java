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
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<NoteResponseDTO> getNote(String identification) {
        List<Note> customers = noteRepository.findByIdentificationIsNotNullAndIdentification(identification);
        return customers.stream().map(NoteMapper::toDTO).toList();
    }

    public NoteResponseDTO createNote(NoteRequestDTO noteRequestDTO) {
        Note newNote = noteRepository.save(NoteMapper.toModel(noteRequestDTO));
        return NoteMapper.toDTO(newNote);
    }

    public NoteResponseDTO updateNote(UUID id, NoteRequestDTO noteRequestDTO) {
        Note note = noteRepository.findById(id).orElseThrow(() -> new NoteNotFoundException("Note not found with ID: " + id));

        note.setTitle(noteRequestDTO.getTitle());
        note.setContent(noteRequestDTO.getContent());

        Note updatedNote = noteRepository.save(note);

        return NoteMapper.toDTO(updatedNote);
    }

    public void deleteNote(UUID id) {
        noteRepository.deleteById(id);
    }

}
