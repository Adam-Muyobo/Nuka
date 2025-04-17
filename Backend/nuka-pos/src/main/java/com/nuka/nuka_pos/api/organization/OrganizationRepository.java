package com.nuka.nuka_pos.api.organization;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for accessing Organization data.
 */
public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    Organization findByCode(String organizationCode);

    Optional<Organization> findByVerificationCode(String verificationCode);

    boolean existsByEmail(String organizationEmail);
}

