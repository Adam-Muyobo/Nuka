package com.nuka.nuka_pos.api.authentication.service;

import com.nuka.nuka_pos.api.authentication.dto.LoginRequest;
import com.nuka.nuka_pos.api.authentication.dto.LoginResponse;
import com.nuka.nuka_pos.api.authentication.util.JwtUtil;
import com.nuka.nuka_pos.api.user.User;
import com.nuka.nuka_pos.api.user.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public AuthenticationService(AuthenticationManager authenticationManager, UserRepository userRepository, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public LoginResponse login(LoginRequest loginRequest) {
        // Create authentication token with credentials from the login request
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());

        // Authenticate the user using AuthenticationManager
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // Load the user details after successful authentication
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();

        // Generate JWT token for the authenticated user
        String token = jwtUtil.generateToken(String.valueOf(userDetails));

        // Return the login response with user data and the generated token
        return new LoginResponse(
                token,
                user.getId(), // User role
                user.getRole().getId(),
                user.getRole().getName()// User ID
        );
    }
}
