package com.nuka.nuka_pos.api.auditlog;

import com.nuka.nuka_pos.api.auditlog.enums.AuditActionType;
import com.nuka.nuka_pos.api.branch.Branch;
import com.nuka.nuka_pos.api.organization.Organization;
import com.nuka.nuka_pos.api.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuditActionType action;

    @Column(nullable = false)
    private String entityName;

    @Column(nullable = false, updatable = false)
    private LocalDateTime timestamp;

    @Column(columnDefinition = "TEXT")
    private String metadata;

    @PrePersist
    public void prePersist() {
        this.timestamp = LocalDateTime.now();
    }
}
