package com.pm.filemanagementsystembackend.repository;

import com.pm.filemanagementsystembackend.model.Auth;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<Auth, Email> {
    Optional<Auth> findByEmail(String email);
}
