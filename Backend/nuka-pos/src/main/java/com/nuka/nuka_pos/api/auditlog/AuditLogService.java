package com.nuka.nuka_pos.api.auditlog;

import com.nuka.nuka_pos.api.auditlog.enums.AuditActionType;
import com.nuka.nuka_pos.api.auditlog.exceptions.AuditLogNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public AuditLogService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    public List<AuditLogResponse> getAllLogs() {
        return auditLogRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public AuditLogResponse getLogById(Long id) {
        AuditLog log = auditLogRepository.findById(id)
                .orElseThrow(() -> new AuditLogNotFoundException("Audit log not found with id: " + id));
        return mapToResponse(log);
    }

    public List<AuditLogResponse> getLogsByUserId(Long userId) {
        return auditLogRepository.findByUserId(userId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<AuditLogResponse> getLogsByOrganizationId(Long organizationId) {
        return auditLogRepository.findByOrganizationId(organizationId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<AuditLogResponse> getLogsByBranchId(Long branchId) {
        return auditLogRepository.findByBranchId(branchId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<AuditLogResponse> getLogsByAction(String action) {
        AuditActionType actionType = AuditActionType.valueOf(action.toUpperCase());
        return auditLogRepository.findByAction(actionType).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<AuditLogResponse> getLogsByEntityName(String entityName) {
        return auditLogRepository.findByEntityName(entityName).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private AuditLogResponse mapToResponse(AuditLog log) {
        return new AuditLogResponse(
                log.getId(),
                log.getUser().getId(),
                log.getOrganization().getId(),
                log.getBranch().getId(),
                log.getAction().name(),
                log.getEntityName(),
                log.getTimestamp(),
                log.getMetadata()
        );
    }
}
