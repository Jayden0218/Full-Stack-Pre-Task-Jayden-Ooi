package com.pm.filemanagementsystembackend.controller;

import com.pm.filemanagementsystembackend.dto.NoteRequestDTO;
import com.pm.filemanagementsystembackend.dto.NoteResponseDTO;
import com.pm.filemanagementsystembackend.service.NoteAuthService;
import jakarta.validation.groups.Default;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/auth/notes")
public class NoteAuthController{

    private final NoteAuthService noteAuthService;

    public NoteAuthController(NoteAuthService noteAuthService) {
        this.noteAuthService = noteAuthService;
    }

    @GetMapping("/{email}")
    public ResponseEntity<List<NoteResponseDTO>> getNoteAuth(@PathVariable String email){
        List<NoteResponseDTO> notes = noteAuthService.getNoteAuth(email);
        return ResponseEntity.ok().body(notes);
    }

    @PostMapping
    public ResponseEntity<NoteResponseDTO> createNoteAuth(@Validated({Default.class}) @RequestBody NoteRequestDTO noteRequestDTO) {
        NoteResponseDTO noteResponseDTO = noteAuthService.createNoteAuth(noteRequestDTO);
        return ResponseEntity.ok().body(noteResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteResponseDTO> updateNoteAuth(@PathVariable UUID id, @Validated({Default.class}) @RequestBody NoteRequestDTO noteRequestDTO) {
        NoteResponseDTO noteResponseDTO = noteAuthService.updateNoteAuth(id, noteRequestDTO);
        return ResponseEntity.ok().body(noteResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNoteAuth(@PathVariable UUID id) {
        noteAuthService.deleteNoteAuth(id);
        return ResponseEntity.noContent().build();
    }


}

