package com.nuka.nuka_pos.api.purchase_order_detail.exceptions;

public class PurchaseOrderDetailNotFoundException extends RuntimeException {
    public PurchaseOrderDetailNotFoundException(String message) {
        super(message);
    }
}
