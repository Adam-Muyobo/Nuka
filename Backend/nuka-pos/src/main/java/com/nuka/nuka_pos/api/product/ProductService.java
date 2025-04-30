package com.nuka.nuka_pos.api.product;

import com.nuka.nuka_pos.api.product.exceptions.ProductNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
        return mapToResponse(product);
    }

    public List<ProductResponse> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> getProductsByOrganization(Long organizationId) {
        return productRepository.findByOrganizationId(organizationId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }


    public void createProduct(Product product) {
        productRepository.save(product);
    }

    public void updateProduct(Long id, Product updatedData) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));

        if (updatedData.getName() != null) existing.setName(updatedData.getName());
        if (updatedData.getDescription() != null) existing.setDescription(updatedData.getDescription());
        if (updatedData.getPrice() != null) existing.setPrice(updatedData.getPrice());
        if (updatedData.getCost() != null) existing.setCost(updatedData.getCost());
        if (updatedData.getBarcode() != null) existing.setBarcode(updatedData.getBarcode());
        if (updatedData.getIsActive() != null) existing.setIsActive(updatedData.getIsActive());
        if (updatedData.getOrganization() != null) existing.setOrganization(updatedData.getOrganization());
        if (updatedData.getCategory() != null) existing.setCategory(updatedData.getCategory());

        productRepository.save(existing);
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    private ProductResponse mapToResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCost(),
                product.getBarcode(),
                product.getIsActive(),
                product.getOrganization().getId(),
                product.getCategory().getId()
        );
    }
}
