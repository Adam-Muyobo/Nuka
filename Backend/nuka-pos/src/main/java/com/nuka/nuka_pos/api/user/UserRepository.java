package com.nuka.nuka_pos.api.user;

import com.nuka.nuka_pos.api.user.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByOrganizationId(Long organizationId);
    List<User> findByBranchId(Long branchId);
    List<User> findByRoleAndOrganizationId(Role role, Long organizationId);
    Optional<User> findByUsername(String username);
}
