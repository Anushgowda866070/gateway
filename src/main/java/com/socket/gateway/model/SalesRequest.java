package com.socket.gateway.model;

import com.socket.gateway.enums.EWallet;
import com.socket.gateway.enums.TransactionType;

public class SalesRequest {
    private String merchantId;
    private  String gatewayReference;
    private MoneyEntity moneyEntity;
    private  String recurrenceFlag;
    private CardEntity cardEntity;
    private  AcceptorDetails acceptorDetails;
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

    public MoneyEntity getMoneyEntity() {
        return moneyEntity;
    }

    public void setMoneyEntity(MoneyEntity moneyEntity) {
        this.moneyEntity = moneyEntity;
    }

    public String getRecurrenceFlag() {
        return recurrenceFlag;
    }

    public void setRecurrenceFlag(String recurrenceFlag) {
        this.recurrenceFlag = recurrenceFlag;
    }

    public CardEntity getCardEntity() {
        return cardEntity;
    }

    public void setCardEntity(CardEntity cardEntity) {
        this.cardEntity = cardEntity;
    }

    public AcceptorDetails getAcceptorDetails() {
        return acceptorDetails;
    }

    public void setAcceptorDetails(AcceptorDetails acceptorDetails) {
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

