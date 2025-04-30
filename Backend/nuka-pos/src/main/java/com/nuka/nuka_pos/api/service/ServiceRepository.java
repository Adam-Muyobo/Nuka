package com.nuka.nuka_pos.api.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {
    // Find services by category
    List<ServiceEntity> findByCategoryId(Long categoryId);

    Collection<ServiceEntity> findByOrganizationId(Long organizationId);
}
