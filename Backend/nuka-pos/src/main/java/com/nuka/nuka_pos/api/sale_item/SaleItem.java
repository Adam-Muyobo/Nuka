package com.nuka.nuka_pos.api.sale_item;

import com.nuka.nuka_pos.api.product.Product;
import com.nuka.nuka_pos.api.sale.Sale;
import com.nuka.nuka_pos.api.service.ServiceEntity;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "sale_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaleItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "sale_id")
    private Sale sale;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private ServiceEntity service;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Double unitPrice;

    @PrePersist
    @PreUpdate
    private void validateAssociation() {
        if ((product == null && service == null) || (product != null && service != null)) {
            throw new IllegalArgumentException("SaleItem must have either a product or a service, but not both.");
        }
    }

    @Transient
    public Double getTotalPrice() {
        return  quantity * unitPrice;
    }
}
