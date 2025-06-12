package com.ecommerce.backecommerce.dto;

import java.util.List;

public class MercadoPagoPaymentRequestDTO {
    private List<Long> productIds;

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }
}
