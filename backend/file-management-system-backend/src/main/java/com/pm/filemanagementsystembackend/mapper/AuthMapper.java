package com.pm.filemanagementsystembackend.mapper;

import com.pm.filemanagementsystembackend.dto.AuthRequestCreateDTO;
import com.pm.filemanagementsystembackend.model.Auth;

public class AuthMapper {
    public static Auth toModel(AuthRequestCreateDTO AuthRequestCreateDTO) {
        Auth auth = new Auth();
        auth.setEmail(AuthRequestCreateDTO.getEmail());
        auth.setPassword(AuthRequestCreateDTO.getPassword());
        auth.setName(AuthRequestCreateDTO.getName());

        return auth;
    }
}
