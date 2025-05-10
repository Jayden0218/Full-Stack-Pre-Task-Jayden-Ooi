package com.pm.filemanagementsystembackend.dto;

import jakarta.validation.constraints.NotBlank;

public class AuthRequestCreateDTO {
    @NotBlank(message="Email is required")
    private String email;

    @NotBlank(message = "password is required")
    private String password;

    private String name;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }
}
