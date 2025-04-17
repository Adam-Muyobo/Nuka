package com.nuka.nuka_pos.api.smart_invoice;

import com.nuka.nuka_pos.api.smart_invoice.enums.SmartInvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SmartInvoiceRecordRepository extends JpaRepository<SmartInvoiceRecord, Long> {

    // Find by status
    List<SmartInvoiceRecord> findByStatus(SmartInvoiceStatus status);

    // Find by organizationId
    List<SmartInvoiceRecord> findByOrganizationId(Long organizationId);

    // Find by customerId
    List<SmartInvoiceRecord> findByCustomerId(Long customerId);
}
