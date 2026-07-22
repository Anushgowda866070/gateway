package com.socket.gateway.dto;

import com.socket.gateway.enums.EWallet;
import com.socket.gateway.enums.TransactionType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SalesRequestDTO {

    @NotBlank(message = "Merchant Id is required")
    private String merchantId;

    @NotBlank(message = "Gateway Reference is required")
    private String gatewayReference;

    @NotBlank(message = "Recurrence Flag is required")
    private String recurrenceFlag;

    @Valid
    @NotNull(message = "Money details are required")
    private MoneyDTO moneyEntity;

    @Valid
    @NotNull(message = "Card details are required")
    private CardDTO cardEntity;

    @Valid
    @NotNull(message = "Acceptor details are required")
    private AcceptorDetailsDTO acceptorDetails;

    @NotNull(message = "EWallet is required")
    private EWallet eWallet;

    @NotNull(message = "Transaction Type is required")
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
}
