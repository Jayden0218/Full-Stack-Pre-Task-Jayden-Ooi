package com.pm.filemanagementsystembackend.controller;

import com.pm.filemanagementsystembackend.dto.AuthRequestCreateDTO;
import com.pm.filemanagementsystembackend.dto.AuthRequestLoginDTO;
import com.pm.filemanagementsystembackend.dto.AuthResponseCreateDTO;
import com.pm.filemanagementsystembackend.dto.AuthResponseLoginDTO;
import com.pm.filemanagementsystembackend.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseLoginDTO> login(
            @RequestBody AuthRequestLoginDTO authRequestLoginDTO) {
        Optional<AuthResponseLoginDTO> tokenOptional = authService.authenticateLogIn(authRequestLoginDTO);

        if (tokenOptional.isEmpty() || tokenOptional.get().token().isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        AuthResponseLoginDTO authResponseLoginDTO = tokenOptional.get();

        authService.changeIdentification(authRequestLoginDTO);

        String token = authResponseLoginDTO.token();
        String name = authResponseLoginDTO.name();
        return ResponseEntity.ok(new AuthResponseLoginDTO(token, name));
    }

    @PostMapping("/create")
    public ResponseEntity<AuthResponseCreateDTO> create(
            @RequestBody AuthRequestCreateDTO authRequestCreateDTO) {
        Optional<String> tokenOptional = authService.authenticateCreate(authRequestCreateDTO);

        if (tokenOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        authService.changeIdentification(authRequestCreateDTO);
        String token = tokenOptional.get();
        return ResponseEntity.ok(new AuthResponseCreateDTO(token));
    }

    @GetMapping("/validate")
    public ResponseEntity<Void> validateToken(@RequestHeader("Authorization") String authHeader) {
        log.info("2. starting to check");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.info("2. check fail");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return authService.validateToken(authHeader.substring(7))
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
