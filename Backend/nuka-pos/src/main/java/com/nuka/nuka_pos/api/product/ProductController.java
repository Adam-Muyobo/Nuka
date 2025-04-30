package com.nuka.nuka_pos.api.product;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("/category/{categoryId}")
    public List<ProductResponse> getProductsByCategory(@PathVariable Long categoryId) {
        return productService.getProductsByCategory(categoryId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody Product product) {
        productService.createProduct(product);
    }

    @PutMapping("/{id}")
    public void updateProduct(@PathVariable Long id, @RequestBody Product updatedData) {
        productService.updateProduct(id, updatedData);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @GetMapping("/organization/{organizationId}")
    public List<ProductResponse> getProductsByOrganization(@PathVariable Long organizationId) {
        return productService.getProductsByOrganization(organizationId);
    }

}
