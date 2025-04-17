package com.nuka.nuka_pos.api.authentication.dto;

import lombok.Data;

/**
 * Request DTO for user login.
 */
@Data
public class LoginRequest {
    private String username; // Only username, no email login
    private String password;
}
