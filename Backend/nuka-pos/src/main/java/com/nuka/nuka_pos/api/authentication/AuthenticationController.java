package com.nuka.nuka_pos.api.authentication;

import com.nuka.nuka_pos.api.authentication.dto.LoginRequest;
import com.nuka.nuka_pos.api.authentication.dto.LoginResponse;
import com.nuka.nuka_pos.api.authentication.dto.RegistrationRequest;
import com.nuka.nuka_pos.api.authentication.dto.RegistrationResponse;
import com.nuka.nuka_pos.api.authentication.service.AuthenticationService;
import com.nuka.nuka_pos.api.authentication.service.RegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/secure/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final RegistrationService registrationService;

    public AuthenticationController(AuthenticationService authenticationService, RegistrationService registrationService) {
        this.authenticationService = authenticationService;
        this.registrationService = registrationService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> register(@RequestBody RegistrationRequest request) {
        RegistrationResponse response = registrationService.register(request); // Corrected method name
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = authenticationService.login(request);
        return ResponseEntity.ok(response);
    }
}
