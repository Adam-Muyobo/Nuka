package com.nuka.nuka_pos.api.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Simplified response DTO after successful login.
 */
@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;

    private Long userId;
    private Long roleId;
    private String roleName;
}
