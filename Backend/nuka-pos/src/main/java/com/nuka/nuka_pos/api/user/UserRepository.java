package com.nuka.nuka_pos.api.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByOrganizationId(Long organizationId);
    List<User> findByBranchId(Long branchId);

    Optional<User> findByEmail(String identifier);

    Optional<User> findByUsername(String identifier);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
