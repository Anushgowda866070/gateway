package com.socket.gateway.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public class MoneyDTO {

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount should be greater than zero")
    private Double amount;

    @NotBlank(message = "Currency Code is required")
    private String currencyCode;

    @PositiveOrZero(message = "Cashback cannot be negative")
    private Double cashback;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Double getCashback() {
        return cashback;
    }

    public void setCashback(Double cashback) {
        this.cashback = cashback;
    }
}
