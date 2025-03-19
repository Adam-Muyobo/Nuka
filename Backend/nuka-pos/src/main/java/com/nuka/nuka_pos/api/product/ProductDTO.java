package com.nuka.nuka_pos.api.product;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    private Long id;
    private String name;
    private String barcode;
    private Double price;
    private Integer stockQuantity;
}
