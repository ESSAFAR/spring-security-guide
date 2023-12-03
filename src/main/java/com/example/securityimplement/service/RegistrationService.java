package com.example.securityimplement.service;


import com.example.securityimplement.dto.RegistrationRequest;
import com.example.securityimplement.dto.RegistrationResponse;
import com.example.securityimplement.model.User;
import com.example.securityimplement.reposiroty.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {
    PasswordEncoder passwordEncoder;
    UserRepository repository;
    JwtService jwtService;

    public RegistrationResponse register(RegistrationRequest request){
        User user = new User();
        user.setRole(request.getRole());
        user.setLastName(request.getLastName());
        user.setFirstName(request.getFirstName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        //This normally should be User.builder....
        repository.save(user);
        String token = jwtService.generateToken(user);
        return new RegistrationResponse(token);
    }
}
