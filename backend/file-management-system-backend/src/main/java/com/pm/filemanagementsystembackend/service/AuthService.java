package com.pm.filemanagementsystembackend.service;

import com.pm.filemanagementsystembackend.dto.AuthRequestCreateDTO;
import com.pm.filemanagementsystembackend.dto.AuthRequestLoginDTO;
import com.pm.filemanagementsystembackend.dto.AuthResponseLoginDTO;
import com.pm.filemanagementsystembackend.exception.UserAlreadyExistsException;
import com.pm.filemanagementsystembackend.exception.UserNotFoundException;
import com.pm.filemanagementsystembackend.mapper.AuthMapper;
import com.pm.filemanagementsystembackend.model.Auth;
import com.pm.filemanagementsystembackend.model.Note;
import com.pm.filemanagementsystembackend.repository.AuthRepository;
import com.pm.filemanagementsystembackend.repository.NoteRepository;
import com.pm.filemanagementsystembackend.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final NoteRepository noteRepository;

    public AuthService(AuthRepository authRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil,NoteRepository noteRepository) {
        this.authRepository = authRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.noteRepository = noteRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    public Optional<String> authenticateCreate(AuthRequestCreateDTO authRequestCreateDTO) {
        // Check if user exists
        Optional<Auth> authOpt = authRepository.findByEmail(authRequestCreateDTO.getEmail());
        Auth auth;

        if (authOpt.isEmpty()) {
            logger.debug("User not found, creating new user: {}", authRequestCreateDTO.getEmail());

            // Hash password and set it
            String encodedPassword = passwordEncoder.encode(authRequestCreateDTO.getPassword());
            logger.debug("Encoded password: {}", encodedPassword);  // Logs the BCrypt hash structure

            Auth newAuth = AuthMapper.toModel(authRequestCreateDTO);
            newAuth.setPassword(encodedPassword);  // Set the encoded password before saving
            auth = authRepository.save(newAuth);
            logger.debug("New user created with encoded password: {}", encodedPassword); // For debugging
        } else {
           throw new UserAlreadyExistsException("User already exists");
        }

        // If authentication passed, generate the token
        String token = jwtUtil.generateToken(auth.getEmail(), auth.getName());
        return Optional.of(token);
    }

    public Optional<AuthResponseLoginDTO> authenticateLogIn(AuthRequestLoginDTO authRequestLoginDTO) {
        // Check if user exists
        Optional<Auth> authOpt = authRepository.findByEmail(authRequestLoginDTO.getEmail());
        Auth auth;

        if (authOpt.isEmpty()) {
            throw new UserNotFoundException("User not found");
        } else {
            auth = authOpt.get();
            logger.debug("User found, verifying password for email: {}", auth.getEmail());

            // Check if the passwords match
            if (!passwordEncoder.matches(authRequestLoginDTO.getPassword(), auth.getPassword())) {
                logger.warn("Password mismatch for email: {}", authRequestLoginDTO.getEmail());
                return Optional.empty();  // Invalid password
            }
        }

        // If authentication passed, generate the token
        String token = jwtUtil.generateToken(auth.getEmail(), auth.getName());

        return Optional.of(new AuthResponseLoginDTO(token,auth.getName()));
    }

    public boolean validateToken(String token) {
        try{
            jwtUtil.validateToken(token);
            return true;
        } catch(JwtException e){
            return false;
        }
    }

    public void changeIdentification(AuthRequestLoginDTO authRequestLoginDTO) {
        logger.info("Change identification {}", authRequestLoginDTO.getEmail());
        List<Note> notes = noteRepository.findByIdentificationIsNotNullAndIdentification(authRequestLoginDTO.getIdentification());
        logger.info("Notes found for identification: {}", notes);
        for(Note note : notes) {
            note.setIdentification(authRequestLoginDTO.getEmail());
            noteRepository.save(note);
        }
    }

    public void changeIdentification(AuthRequestCreateDTO authRequestCreateDTO) {
        logger.info("Change identification {}", authRequestCreateDTO);
        List<Note> notes = noteRepository.findByIdentificationIsNotNullAndIdentification(authRequestCreateDTO.getIdentification());
        logger.info("Notes found for identification: {}", notes);

        for(Note note : notes) {
            note.setIdentification(authRequestCreateDTO.getEmail());

            noteRepository.save(note);
        }
    }
}
