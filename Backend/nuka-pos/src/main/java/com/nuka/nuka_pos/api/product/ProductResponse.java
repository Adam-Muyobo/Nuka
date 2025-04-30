    package com.nuka.nuka_pos.api.product;
    
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;
    
    import java.math.BigDecimal;
    
    /**
     * ProductResponse is used to send product data to the client.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class ProductResponse {
        private Long id;
        private String name;
        private String description;
        private BigDecimal price;
        private BigDecimal cost;
        private String barcode;
        private Boolean isActive;
        private Long organizationId;
        private Long categoryId;
    }
