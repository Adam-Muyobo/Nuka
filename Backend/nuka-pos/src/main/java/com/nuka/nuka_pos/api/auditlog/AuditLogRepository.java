package com.nuka.nuka_pos.api.auditlog;

import com.nuka.nuka_pos.api.auditlog.enums.AuditActionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    List<AuditLog> findByUserId(Long userId);

    List<AuditLog> findByOrganizationId(Long organizationId);

    List<AuditLog> findByBranchId(Long branchId);

    List<AuditLog> findByAction(AuditActionType action);

    List<AuditLog> findByEntityName(String entityName);
}
