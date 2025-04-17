package com.nuka.nuka_pos.api.role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleRequest {
    private Long id;
    private String name;
    private String description;
}
