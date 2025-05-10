package com.pm.filemanagementsystembackend.controller;

import com.pm.filemanagementsystembackend.dto.NoteRequestDTO;
import com.pm.filemanagementsystembackend.dto.NoteResponseDTO;
import com.pm.filemanagementsystembackend.service.NoteService;
import jakarta.validation.groups.Default;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/{identification}")
    public ResponseEntity<List<NoteResponseDTO>> getNote(@PathVariable String identification) {
        List<NoteResponseDTO> notes = noteService.getNote(identification);
        return ResponseEntity.ok().body(notes);
    }

    @PostMapping
    public ResponseEntity<NoteResponseDTO> createNote(@Validated({Default.class}) @RequestBody NoteRequestDTO noteRequestDTO) {
        NoteResponseDTO noteResponseDTO = noteService.createNote(noteRequestDTO);
        return ResponseEntity.ok().body(noteResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteResponseDTO> updateNote(@PathVariable UUID id, @Validated({Default.class}) @RequestBody NoteRequestDTO noteRequestDTO) {
        NoteResponseDTO noteResponseDTO = noteService.updateNote(id, noteRequestDTO);
        return ResponseEntity.ok().body(noteResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable UUID id) {
        noteService.deleteNote(id);
        return ResponseEntity.noContent().build();
    }




}
