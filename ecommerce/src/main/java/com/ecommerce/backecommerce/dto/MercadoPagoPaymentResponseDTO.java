package com.ecommerce.backecommerce.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MercadoPagoPaymentResponseDTO {
    private String preferenceId;
    private String initPoint;

    public MercadoPagoPaymentResponseDTO(String preferenceId, String initPoint) {
        this.preferenceId = preferenceId;
        this.initPoint = initPoint;
    }

    public void setPreferenceId(String preferenceId) {
        this.preferenceId = preferenceId;
    }

    public void setInitPoint(String initPoint) {
        this.initPoint = initPoint;
    }
}
