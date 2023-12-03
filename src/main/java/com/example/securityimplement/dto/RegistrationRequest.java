package com.example.securityimplement.dto;

import com.example.securityimplement.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class RegistrationRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;

}
