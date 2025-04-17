package com.nuka.nuka_pos.api.branch;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Branch entity.
 */
@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {
    List<Branch> findByOrganizationId(Long organizationId);
}
