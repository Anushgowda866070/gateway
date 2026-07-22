package com.socket.gateway.model;

public class AcceptorDetails {
    private String merchantName;
    private String subMerchantId;
    private MerchantContactDetails merchantContactDetails;

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getSubMerchantId() {
        return subMerchantId;
    }

    public void setSubMerchantId(String subMerchantId) {
        this.subMerchantId = subMerchantId;
    }

    public MerchantContactDetails getMerchantContactDetails() {
        return merchantContactDetails;
    }

    public void setMerchantContactDetails(MerchantContactDetails merchantContactDetails) {
        this.merchantContactDetails = merchantContactDetails;
    }
}
