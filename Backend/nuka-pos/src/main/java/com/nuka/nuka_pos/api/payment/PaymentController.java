package com.nuka.nuka_pos.api.payment;

import com.nuka.nuka_pos.api.payment.enums.PaymentMethod;
import com.nuka.nuka_pos.api.payment.enums.PaymentStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public List<PaymentResponse> getAllPayments() {
        return paymentService.getAllPayments();
    }

    @GetMapping("/{id}")
    public PaymentResponse getPaymentById(@PathVariable Long id) {
        return paymentService.getPaymentById(id);
    }

    @GetMapping("/status/{status}")
    public List<PaymentResponse> getPaymentsByStatus(@PathVariable PaymentStatus status) {
        return paymentService.getPaymentsByStatus(status);
    }

    @GetMapping("/method/{paymentMethod}")
    public List<PaymentResponse> getPaymentsByPaymentMethod(@PathVariable PaymentMethod paymentMethod) {
        return paymentService.getPaymentsByPaymentMethod(paymentMethod);
    }

    @GetMapping("/sale/{saleId}")
    public List<PaymentResponse> getPaymentsBySaleId(@PathVariable Long saleId) {
        return paymentService.getPaymentsBySaleId(saleId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createPayment(@RequestBody Payment payment) {
        paymentService.createPayment(payment);
    }

    @PutMapping("/{id}")
    public void updatePayment(@PathVariable Long id, @RequestBody Payment updatedData) {
        paymentService.updatePayment(id, updatedData);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
    }
}
