package com.pm.filemanagementsystembackend.mapper;

import com.pm.filemanagementsystembackend.dto.NoteRequestDTO;
import com.pm.filemanagementsystembackend.dto.NoteResponseDTO;
import com.pm.filemanagementsystembackend.model.Note;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NoteMapper {
    public static NoteResponseDTO toDTO(Note note) {
        NoteResponseDTO noteResponseDTO = new NoteResponseDTO();
        noteResponseDTO.setId(note.getId().toString());
        noteResponseDTO.setTitle(note.getTitle());
        noteResponseDTO.setContent(note.getContent());
        noteResponseDTO.setColor(note.getColor());
        noteResponseDTO.setCreatedAt(note.getCreatedAt().toString());
        noteResponseDTO.setUpdatedAt(note.getUpdatedAt().toString());
        noteResponseDTO.setIdentification(note.getIdentification());
        return noteResponseDTO;
    }

    public static Note toModel(NoteRequestDTO noteRequestDTO) {
        Note note = new Note();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        note.setTitle(noteRequestDTO.getTitle());
        note.setContent(noteRequestDTO.getContent());
        note.setColor(noteRequestDTO.getColor());
        note.setCreatedAt(LocalDateTime.parse(noteRequestDTO.getCreatedAt(), formatter));
        note.setUpdatedAt(LocalDateTime.parse(noteRequestDTO.getUpdatedAt(), formatter));
        note.setIdentification(noteRequestDTO.getIdentification());
        return note;
    }
}
