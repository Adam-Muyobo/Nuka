package com.nuka.nuka_pos.api.taxtype;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * TaxTypeRepository handles CRUD operations for TaxType entities.
 */
public interface TaxTypeRepository extends JpaRepository<TaxType, Long> {

    boolean existsByName(String name);
}
