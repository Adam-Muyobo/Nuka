package com.nuka.nuka_pos.api.smart_invoice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/smart-invoices")
public class SmartInvoiceRecordController {

    private final SmartInvoiceRecordService smartInvoiceRecordService;

    public SmartInvoiceRecordController(SmartInvoiceRecordService smartInvoiceRecordService) {
        this.smartInvoiceRecordService = smartInvoiceRecordService;
    }

    @GetMapping
    public List<SmartInvoiceRecordResponse> getAllSmartInvoiceRecords() {
        return smartInvoiceRecordService.getAllSmartInvoiceRecords();
    }

    @GetMapping("/{id}")
    public SmartInvoiceRecordResponse getSmartInvoiceRecordById(@PathVariable Long id) {
        return smartInvoiceRecordService.getSmartInvoiceRecordById(id);
    }

    @GetMapping("/status/{status}")
    public List<SmartInvoiceRecordResponse> getSmartInvoiceRecordsByStatus(@PathVariable String status) {
        return smartInvoiceRecordService.getSmartInvoiceRecordsByStatus(status);
    }

    @GetMapping("/organization/{organizationId}")
    public List<SmartInvoiceRecordResponse> getSmartInvoiceRecordsByOrganization(@PathVariable Long organizationId) {
        return smartInvoiceRecordService.getSmartInvoiceRecordsByOrganization(organizationId);
    }

    @GetMapping("/customer/{customerId}")
    public List<SmartInvoiceRecordResponse> getSmartInvoiceRecordsByCustomer(@PathVariable Long customerId) {
        return smartInvoiceRecordService.getSmartInvoiceRecordsByCustomer(customerId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SmartInvoiceRecordResponse createSmartInvoiceRecord(@RequestBody SmartInvoiceRecord smartInvoiceRecord) {
        return smartInvoiceRecordService.createSmartInvoiceRecord(smartInvoiceRecord);
    }

    @PutMapping("/{id}")
    public SmartInvoiceRecordResponse updateSmartInvoiceRecord(@PathVariable Long id, @RequestBody SmartInvoiceRecord updatedRecord) {
        return smartInvoiceRecordService.updateSmartInvoiceRecord(id, updatedRecord);
    }
}
