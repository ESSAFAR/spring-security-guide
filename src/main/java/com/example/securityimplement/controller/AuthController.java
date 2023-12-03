package com.example.securityimplement.controller;


import com.example.securityimplement.dto.AuthenticationRequest;
import com.example.securityimplement.dto.AuthenticationResponse;
import com.example.securityimplement.dto.RegistrationRequest;
import com.example.securityimplement.dto.RegistrationResponse;
import com.example.securityimplement.service.AuthenticationService;
import com.example.securityimplement.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final RegistrationService registrationService;
    private final AuthenticationService authenticationService;



    @GetMapping("/register")
    public ResponseEntity<String> willRegister(){
        return ResponseEntity.ok("Will register");
    }



    @PostMapping ("/register")
    public ResponseEntity<RegistrationResponse> register(@RequestBody RegistrationRequest request){
        RegistrationResponse response = registrationService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticateRequest(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authenticationService.authenticationResponse(request));
    }


}
