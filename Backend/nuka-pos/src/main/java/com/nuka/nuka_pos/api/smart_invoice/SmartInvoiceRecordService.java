package com.nuka.nuka_pos.api.smart_invoice;

import com.nuka.nuka_pos.api.smart_invoice.enums.SmartInvoiceStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SmartInvoiceRecordService {

    private final SmartInvoiceRecordRepository smartInvoiceRecordRepository;

    public SmartInvoiceRecordService(SmartInvoiceRecordRepository smartInvoiceRecordRepository) {
        this.smartInvoiceRecordRepository = smartInvoiceRecordRepository;
    }

    public List<SmartInvoiceRecordResponse> getAllSmartInvoiceRecords() {
        return smartInvoiceRecordRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public SmartInvoiceRecordResponse getSmartInvoiceRecordById(Long id) {
        SmartInvoiceRecord smartInvoiceRecord = smartInvoiceRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SmartInvoiceRecord not found with id: " + id));
        return mapToResponse(smartInvoiceRecord);
    }

    public List<SmartInvoiceRecordResponse> getSmartInvoiceRecordsByStatus(String status) {
        SmartInvoiceStatus smartInvoiceStatus = SmartInvoiceStatus.valueOf(status.toUpperCase());
        return smartInvoiceRecordRepository.findByStatus(smartInvoiceStatus).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<SmartInvoiceRecordResponse> getSmartInvoiceRecordsByOrganization(Long organizationId) {
        return smartInvoiceRecordRepository.findByOrganizationId(organizationId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<SmartInvoiceRecordResponse> getSmartInvoiceRecordsByCustomer(Long customerId) {
        return smartInvoiceRecordRepository.findByCustomerId(customerId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public SmartInvoiceRecordResponse createSmartInvoiceRecord(SmartInvoiceRecord smartInvoiceRecord) {
        SmartInvoiceRecord savedRecord = smartInvoiceRecordRepository.save(smartInvoiceRecord);
        return mapToResponse(savedRecord);
    }

    public SmartInvoiceRecordResponse updateSmartInvoiceRecord(Long id, SmartInvoiceRecord updatedRecord) {
        SmartInvoiceRecord existingRecord = smartInvoiceRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SmartInvoiceRecord not found with id: " + id));

        // Update fields
        existingRecord.setInvoiceNumber(updatedRecord.getInvoiceNumber());
        existingRecord.setInvoiceDate(updatedRecord.getInvoiceDate());
        existingRecord.setCustomer(updatedRecord.getCustomer());
        existingRecord.setTotalAmount(updatedRecord.getTotalAmount());
        existingRecord.setTaxAmount(updatedRecord.getTaxAmount());
        existingRecord.setStatus(updatedRecord.getStatus());
        existingRecord.setZraTransactionId(updatedRecord.getZraTransactionId());

        // Save updated record
        SmartInvoiceRecord savedRecord = smartInvoiceRecordRepository.save(existingRecord);
        return mapToResponse(savedRecord);
    }

    private SmartInvoiceRecordResponse mapToResponse(SmartInvoiceRecord smartInvoiceRecord) {
        return new SmartInvoiceRecordResponse(
                smartInvoiceRecord.getId(),
                smartInvoiceRecord.getOrganization().getId(),
                smartInvoiceRecord.getInvoiceNumber(),
                smartInvoiceRecord.getInvoiceDate(),
                smartInvoiceRecord.getCustomer().getId(),
                smartInvoiceRecord.getTotalAmount(),
                smartInvoiceRecord.getTaxAmount(),
                smartInvoiceRecord.getStatus().name(),
                smartInvoiceRecord.getZraTransactionId(),
                smartInvoiceRecord.getCreatedAt(),
                smartInvoiceRecord.getUpdatedAt()
        );
    }
}
