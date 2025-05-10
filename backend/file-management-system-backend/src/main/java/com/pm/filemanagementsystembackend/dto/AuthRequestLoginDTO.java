package com.pm.filemanagementsystembackend.dto;

import jakarta.validation.constraints.NotBlank;

public class AuthRequestLoginDTO {
    @NotBlank(message="Email is required")
    private String email;

    @NotBlank(message = "password is required")
    private String password;

    private String identification;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }
}
