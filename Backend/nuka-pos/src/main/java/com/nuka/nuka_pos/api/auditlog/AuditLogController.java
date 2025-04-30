package com.nuka.nuka_pos.api.auditlog;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit-logs")
public class AuditLogController {

    private final AuditLogService auditLogService;

    public AuditLogController(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @GetMapping
    public List<AuditLogResponse> getAllLogs() {
        return auditLogService.getAllLogs();
    }

    @GetMapping("/{id}")
    public AuditLogResponse getLogById(@PathVariable Long id) {
        return auditLogService.getLogById(id);
    }

    @GetMapping("/user/{userId}")
    public List<AuditLogResponse> getLogsByUserId(@PathVariable Long userId) {
        return auditLogService.getLogsByUserId(userId);
    }

    @GetMapping("/organization/{organizationId}")
    public List<AuditLogResponse> getLogsByOrganizationId(@PathVariable Long organizationId) {
        return auditLogService.getLogsByOrganizationId(organizationId);
    }

    @GetMapping("/branch/{branchId}")
    public List<AuditLogResponse> getLogsByBranchId(@PathVariable Long branchId) {
        return auditLogService.getLogsByBranchId(branchId);
    }

    @GetMapping("/action/{action}")
    public List<AuditLogResponse> getLogsByAction(@PathVariable String action) {
        return auditLogService.getLogsByAction(action);
    }

    @GetMapping("/entity/{entityName}")
    public List<AuditLogResponse> getLogsByEntityName(@PathVariable String entityName) {
        return auditLogService.getLogsByEntityName(entityName);
    }

    @PostMapping
    public AuditLogResponse createAuditLog(@RequestBody @Valid AuditLogResponse request) {
        return auditLogService.createLog(request);
    }

}
