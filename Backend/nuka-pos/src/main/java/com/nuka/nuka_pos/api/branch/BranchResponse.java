package com.nuka.nuka_pos.api.branch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BranchResponse {
    private Long id;
    private String name;
    private String location;
    private String phone;
    private String email;
    private Boolean isHeadOffice;
    private Long organizationId;
    private Long currencyId;
}
