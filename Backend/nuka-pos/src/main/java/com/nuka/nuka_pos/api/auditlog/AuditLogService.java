package com.nuka.nuka_pos.api.auditlog;

import com.nuka.nuka_pos.api.auditlog.enums.AuditActionType;
import com.nuka.nuka_pos.api.auditlog.exceptions.AuditLogNotFoundException;
import com.nuka.nuka_pos.api.organization.OrganizationService;
import com.nuka.nuka_pos.api.user.User;
import com.nuka.nuka_pos.api.user.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;
    private final UserService userService;
    private final OrganizationService organizationService;

    public AuditLogService(AuditLogRepository auditLogRepository, UserService userService,OrganizationService organizationService1) {
        this.auditLogRepository = auditLogRepository;
        this.userService = userService;
        this.organizationService = organizationService1;
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

    public AuditLogResponse createLog(AuditLogResponse request) {
        AuditLog log = new AuditLog();
        User user = userService.fetchUserById(request.getUserId());

        log.setUser(user);
        log.setOrganization(organizationService.fetchOrganizationById(request.getOrganizationId()));
        log.setBranch(user.getBranch());
        log.setAction(AuditActionType.valueOf(request.getAction().toUpperCase()));
        log.setEntityName(request.getEntityName());
        log.setMetadata(request.getMetadata());

        AuditLog savedLog = auditLogRepository.save(log);
        return mapToResponse(savedLog);
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
