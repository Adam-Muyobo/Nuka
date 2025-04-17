package com.nuka.nuka_pos.api.auditlog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogResponse {

    private Long id;
    private Long userId;
    private Long organizationId;
    private Long branchId;
    private String action;
    private String entityName;
    private LocalDateTime timestamp;
    private String metadata;
}
