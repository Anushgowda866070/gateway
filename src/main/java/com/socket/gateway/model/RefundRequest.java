package com.socket.gateway.model;

import com.socket.gateway.enums.TransactionType;

public class RefundRequest {

        private String merchantId;
        private String transactionId;
        private Double refundAmount;
        private TransactionType transactionType;

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(Double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public String toString() {
        return "RefundRequest{" +
                "merchantId='" + merchantId + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", refundAmount=" + refundAmount +
                ", transactionType=" + transactionType +
                '}';
    }
}
