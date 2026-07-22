package com.socket.gateway.dto;

import com.socket.gateway.enums.CardType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class CardDTO {

    @NotBlank(message = "Card Number is required")
    @Pattern(regexp = "\\d{16}",
            message = "Card Number must contain exactly 16 digits")
    private String cardNumber;

    @NotBlank(message = "Card Expiry is required")
    private String cardExpiry;

    @NotBlank(message = "CVV is required")
    @Pattern(regexp = "\\d{3}",
            message = "CVV must contain exactly 3 digits")
    private String cvv;

    @NotNull
    private CardType cardType;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardExpiry() {
        return cardExpiry;
    }

    public void setCardExpiry(String cardExpiry) {
        this.cardExpiry = cardExpiry;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }
}
