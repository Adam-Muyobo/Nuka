package com.nuka.nuka_pos.api.currency;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * CurrencyRepository handles database operations for the Currency entity.
 * It provides methods to fetch and store currency data.
 */
@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    boolean existsByName(String name);
}

