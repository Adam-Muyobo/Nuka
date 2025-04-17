package com.nuka.nuka_pos.api.customer;

import com.nuka.nuka_pos.api.customer.exceptions.CustomerNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public CustomerResponse getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));
        return mapToResponse(customer);
    }

    public void createCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public void updateCustomer(Long id, Customer updatedData) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));

        if (updatedData.getName() != null) existingCustomer.setName(updatedData.getName());
        if (updatedData.getPhone() != null) existingCustomer.setPhone(updatedData.getPhone());
        if (updatedData.getEmail() != null) existingCustomer.setEmail(updatedData.getEmail());
        if (updatedData.getTaxNumber() != null) existingCustomer.setTaxNumber(updatedData.getTaxNumber());
        if (updatedData.getCustomerType() != null) existingCustomer.setCustomerType(updatedData.getCustomerType());
        if (updatedData.getIsActive() != null) existingCustomer.setIsActive(updatedData.getIsActive());
        if (updatedData.getCreatedAt() != null) existingCustomer.setCreatedAt(updatedData.getCreatedAt());
        if (updatedData.getOrganization() != null) existingCustomer.setOrganization(updatedData.getOrganization());
        if (updatedData.getBranch() != null) existingCustomer.setBranch(updatedData.getBranch());

        customerRepository.save(existingCustomer);
    }

    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new CustomerNotFoundException("Customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
    }

    private CustomerResponse mapToResponse(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getPhone(),
                customer.getEmail(),
                customer.getTaxNumber(),
                customer.getCustomerType().name(), // Convert enum to string
                customer.getIsActive(),
                customer.getCreatedAt(),
                customer.getOrganization().getId(),
                customer.getBranch().getId()
        );
    }
}
