package com.nuka.nuka_pos.api.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Response DTO after successful organization and root admin registration.
 */
@Data
@AllArgsConstructor
public class RegistrationResponse {
    private Long organizationId;
    private String organizationName;
    private Long adminUserId;
    private String adminUsername;
    private String message;
}
