package com.socket.gateway.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.socket.gateway.enums.EWallet;
import com.socket.gateway.enums.TransactionType;
import jakarta.validation.constraints.NotBlank;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RefundRequestDTO {

    @NotBlank(message = "Merchant Id is required")
    private String merchantId;

    @NotBlank(message = "Gateway Reference is required")
    private String gatewayReference;

    @NotBlank(message = "Parent Transaction Id is required")
    private String parentTransactionId;

    private String recurrenceFlag;

    private MoneyDTO moneyEntity;

    private CardDTO cardEntity;

    private AcceptorDetailsDTO acceptorDetails;

    private EWallet eWallet;

    private TransactionType transactionType;

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getGatewayReference() {
        return gatewayReference;
    }

    public void setGatewayReference(String gatewayReference) {
        this.gatewayReference = gatewayReference;
    }

    public String getRecurrenceFlag() {
        return recurrenceFlag;
    }

    public void setRecurrenceFlag(String recurrenceFlag) {
        this.recurrenceFlag = recurrenceFlag;
    }

    public MoneyDTO getMoneyEntity() {
        return moneyEntity;
    }

    public void setMoneyEntity(MoneyDTO moneyEntity) {
        this.moneyEntity = moneyEntity;
    }

    public CardDTO getCardEntity() {
        return cardEntity;
    }

    public void setCardEntity(CardDTO cardEntity) {
        this.cardEntity = cardEntity;
    }

    public AcceptorDetailsDTO getAcceptorDetails() {
        return acceptorDetails;
    }

    public void setAcceptorDetails(AcceptorDetailsDTO acceptorDetails) {
        this.acceptorDetails = acceptorDetails;
    }

    public EWallet geteWallet() {
        return eWallet;
    }

    public void seteWallet(EWallet eWallet) {
        this.eWallet = eWallet;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public String getParentTransactionId() {
        return parentTransactionId;
    }

    public void setParentTransactionId(String parentTransactionId) {
        this.parentTransactionId = parentTransactionId;
    }
}
