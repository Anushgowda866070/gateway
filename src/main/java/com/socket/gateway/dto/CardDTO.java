package com.socket.gateway.dto;

import com.socket.gateway.enums.Scheme;
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
    private String cvv;

    @NotNull
    private Scheme scheme;

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

    public Scheme getScheme() {
        return scheme;
    }

    public void setScheme(Scheme scheme) {
        this.scheme = scheme;
    }
}
