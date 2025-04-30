package com.nuka.nuka_pos.api.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
public class ServiceController {

    private final ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @GetMapping
    public List<ServiceResponse> getAllServices() {
        return serviceService.getAllServices();
    }

    @GetMapping("/{id}")
    public ServiceResponse getServiceById(@PathVariable Long id) {
        return serviceService.getServiceById(id);
    }

    @GetMapping("/category/{categoryId}")
    public List<ServiceResponse> getServicesByCategoryId(@PathVariable Long categoryId) {
        return serviceService.getServicesByCategoryId(categoryId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createService(@RequestBody ServiceEntity service) {
        serviceService.createService(service);
    }

    @PutMapping("/{id}")
    public void updateService(@PathVariable Long id, @RequestBody ServiceEntity updatedData) {
        serviceService.updateService(id, updatedData);
    }

    @GetMapping("/organization/{organizationId}")
    public List<ServiceResponse> getServicesByOrganization(@PathVariable Long organizationId) {
        return serviceService.getServicesByOrganization(organizationId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteService(@PathVariable Long id) {
        serviceService.deleteService(id);
    }
}
