package com.nuka.nuka_pos.api.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private String forenames;
    private String surname;
    private String email;
    private String phone;
    private Boolean isActive;
    private String createdAt;
    private Long organizationId;
    private String role;  // Changed to String to hold the enum value
    private Long branchId;
}
