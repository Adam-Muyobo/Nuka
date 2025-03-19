package com.nuka.nuka_pos.api.tax_record;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface TaxRecordRepository extends JpaRepository<TaxRecord, Long> {
    List<TaxRecord> findByTransactionId(Long transactionId);
}
