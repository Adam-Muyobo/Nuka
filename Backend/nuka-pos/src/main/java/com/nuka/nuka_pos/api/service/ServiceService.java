package com.nuka.nuka_pos.api.service;

import com.nuka.nuka_pos.api.service.exceptions.ServiceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceService {

    private final ServiceRepository serviceRepository;

    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public List<ServiceResponse> getAllServices() {
        return serviceRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ServiceResponse getServiceById(Long id) {
        ServiceEntity service = serviceRepository.findById(id)
                .orElseThrow(() -> new ServiceNotFoundException("Service not found with id: " + id));
        return mapToResponse(service);
    }

    public void createService(ServiceEntity service) {
        serviceRepository.save(service);
    }

    public void updateService(Long id, ServiceEntity updatedData) {
        ServiceEntity existingService = serviceRepository.findById(id)
                .orElseThrow(() -> new ServiceNotFoundException("Service not found with id: " + id));

        if (updatedData.getName() != null) existingService.setName(updatedData.getName());
        if (updatedData.getDescription() != null) existingService.setDescription(updatedData.getDescription());
        if (updatedData.getPrice() != null) existingService.setPrice(updatedData.getPrice());
        if (updatedData.getIsActive() != null) existingService.setIsActive(updatedData.getIsActive());
        if (updatedData.getOrganization() != null) existingService.setOrganization(updatedData.getOrganization());
        if (updatedData.getCategory() != null) existingService.setCategory(updatedData.getCategory());

        serviceRepository.save(existingService);
    }

    public void deleteService(Long id) {
        if (!serviceRepository.existsById(id)) {
            throw new ServiceNotFoundException("Service not found with id: " + id);
        }
        serviceRepository.deleteById(id);
    }

    public List<ServiceResponse> getServicesByCategoryId(Long categoryId) {
        List<ServiceEntity> services = serviceRepository.findByCategoryId(categoryId);
        return services.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<ServiceResponse> getServicesByOrganization(Long organizationId) {
        return serviceRepository.findByOrganizationId(organizationId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private ServiceResponse mapToResponse(ServiceEntity service) {
        return new ServiceResponse(
                service.getId(),
                service.getName(),
                service.getDescription(),
                service.getPrice(),
                service.getIsActive(),
                service.getOrganization().getId(),
                service.getCategory().getId()
        );
    }
}
