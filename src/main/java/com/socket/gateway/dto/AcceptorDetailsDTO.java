package com.socket.gateway.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AcceptorDetailsDTO {

    @NotBlank(message = "Acceptor ID is required")
    private String subMerchantId;

    @NotBlank(message = "Acceptor Name is required")
    private String merchantName;

    @Valid
    @NotNull(message = "Merchant contact details are required")
    private MerchantContactDetailsDTO merchantContactDetails;

    public String getSubMerchantId() {
        return subMerchantId;
    }

    public void setSubMerchantId(String subMerchantId) {
        this.subMerchantId = subMerchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public MerchantContactDetailsDTO getMerchantContactDetails() {
        return merchantContactDetails;
    }

    public void setMerchantContactDetails(MerchantContactDetailsDTO merchantContactDetails) {
        this.merchantContactDetails = merchantContactDetails;
    }
}
