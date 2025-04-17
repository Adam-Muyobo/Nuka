package com.nuka.nuka_pos.api.receipt;

import com.nuka.nuka_pos.api.receipt.enums.ReceiptType;
import com.nuka.nuka_pos.api.receipt.exceptions.ReceiptNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReceiptService {

    private final ReceiptRepository receiptRepository;

    public ReceiptService(ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
    }

    public List<ReceiptResponse> getAllReceipts() {
        return receiptRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ReceiptResponse getReceiptById(Long id) {
        Receipt receipt = receiptRepository.findById(id)
                .orElseThrow(() -> new ReceiptNotFoundException("Receipt not found with id: " + id));
        return mapToResponse(receipt);
    }

    public List<ReceiptResponse> getReceiptsByType(ReceiptType type) {
        return receiptRepository.findByType(type).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public void createReceipt(Receipt receipt) {
        receiptRepository.save(receipt);
    }

    public void updateReceipt(Long id, Receipt updatedData) {
        Receipt existing = receiptRepository.findById(id)
                .orElseThrow(() -> new ReceiptNotFoundException("Receipt not found with id: " + id));

        if (updatedData.getType() != null) existing.setType(updatedData.getType());
        if (updatedData.getReceiptDate() != null) existing.setReceiptDate(updatedData.getReceiptDate());
        if (updatedData.getReceiptNumber() != null) existing.setReceiptNumber(updatedData.getReceiptNumber());
        if (updatedData.getRecipientEmail() != null) existing.setRecipientEmail(updatedData.getRecipientEmail());
        if (updatedData.getDelivered() != null) existing.setDelivered(updatedData.getDelivered());
        if (updatedData.getSale() != null) existing.setSale(updatedData.getSale());

        receiptRepository.save(existing);
    }

    public void deleteReceipt(Long id) {
        if (!receiptRepository.existsById(id)) {
            throw new ReceiptNotFoundException("Receipt not found with id: " + id);
        }
        receiptRepository.deleteById(id);
    }

    private ReceiptResponse mapToResponse(Receipt receipt) {
        return new ReceiptResponse(
                receipt.getId(),
                receipt.getSale().getId(),
                receipt.getType().toString(),
                receipt.getReceiptDate(),
                receipt.getReceiptNumber(),
                receipt.getRecipientEmail(),
                receipt.getDelivered(),
                receipt.getCreatedAt(),
                receipt.getUpdatedAt()
        );
    }
}
